package me.efraimgentil.service;

import com.fasterxml.jackson.databind.JsonNode;
import me.efraimgentil.exception.NotFoundException;
import me.efraimgentil.model.*;
import me.efraimgentil.model.Driver;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
@Service
@Lazy(true)
public class RouteService {

  @Autowired JdbcTemplate jdbcTemplate;
  @Autowired GoogleDirectionService directionService;


  public Route createRoute(final Route route ){
    JsonNode jsonRoute = directionService.callGoogleService(route);
    route.setStops( orderStops(jsonRoute, route.getStops()) );
    return route;
  }

  public Driver suggestADriver(final Route route){
    Route orderedRoute = createRoute(route); //Create a route to order the stops
    return nearestDriver( orderedRoute.getLastStop().getPoint() );
  }

  protected Driver nearestDriver( Point point ){
    final  String sql = "select d.id as d_id , d.name as d_name , d.home_location_id as d_home_location_id"
            + " from driver d inner join location l on l.id = d.home_location_id"
            + " ORDER BY l.point <-> GeomFromEWKT(?) LIMIT 1";
    return jdbcTemplate.queryForObject(sql, new Object[]{point.toGeom()}
            , (rs, rowNum) -> mountDriver(rs));
  }

  protected List<Stop> orderStops(JsonNode routeNode, List<Stop> stops) {
    final List<Stop> orderedStops = new ArrayList<>();
    //if( !routeNode.has("waypoint_order") ) throw new NoWayPointOrderException();
    for (JsonNode n : routeNode.get("waypoint_order")) {
      Stop stop = stops.get(n.asInt());
      stop.setOrder( n.asInt() );
      orderedStops.add( stop );
    }
    return orderedStops;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public List<Route> routes() {
    final List<Route> routes = new ArrayList<>();
    final String sql = "SELECT r.*" +
            " , d.id as d_id , d.name as d_name , d.home_location_id as d_home_location_id" +
            " , start.id as start_id , start.name as start_name , start.point as start_point , start.point_name as start_point_name , start.private as start_private " +
            " , ending.id as ending_id , ending.name as ending_name , ending.point as ending_point , ending.point_name as ending_point_name , ending.private as ending_private " +
            " FROM route r" +
            "  left JOIN location start on start.id = r.starting_location_id " +
            "  left JOIN location ending on ending.id = r.ending_location_id " +
            "  left JOIN driver d on d.id = r.driver_id";
    jdbcTemplate.query(sql, new Object[]{}, (RowCallbackHandler) (ResultSet rs) -> {
        routes.add( mountRoute( rs ) );
    });
    return routes;
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public Route get(long id) {
    try {
      final String sql = "SELECT r.*" +
              " , d.id as d_id , d.name as d_name , d.home_location_id as d_home_location_id" +
              " , start.id as start_id , start.name as start_name , start.point as start_point , start.point_name as start_point_name , start.private as start_private " +
              " , ending.id as ending_id , ending.name as ending_name , ending.point as ending_point , ending.point_name as ending_point_name , ending.private as ending_private " +
              " FROM route r" +
              "  left JOIN location start on start.id = r.starting_location_id " +
              "  left JOIN location ending on ending.id = r.ending_location_id " +
              "  left JOIN driver d on d.id = r.driver_id" +
              " WHERE r.id = ?";
      Route route = jdbcTemplate.queryForObject( sql , new Object[]{id}, new RowMapper<Route>() {
        public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
          return mountRoute(rs);
        }
      });
      return route;
    } catch (EmptyResultDataAccessException erdae) {
      throw new NotFoundException();
    }
  }

  @Transactional
  public Route create(final Route route) {
    final String insertIntoSql = "INSERT INTO route ( date ,  starting_location_id , ending_location_id " +
            ", driver_id , created_at ) VALUES ( ? , ? , ? , ? , now() )";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
            new PreparedStatementCreator() {
              public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertIntoSql, new String[]{"id"});
                ps.setDate(1, new Date( route.getDate().getTime() ) );
                ps.setInt(2, route.getStartingLocation().getId());
                ps.setInt(3, route.getEndingLocation().getId());
                ps.setInt(4, route.getDriver().getId());
                return ps;
              }
            }, keyHolder);
    route.setId(keyHolder.getKey().longValue());
    saveStops(route);
    return route;
  }

  private void saveStops(Route route) {
    List<Stop> stops = route.getStops();

    final String insertIntoSql = "INSERT INTO stop ( route_id ,  stop_order , passenger , point ) " +
            " VALUES ( ? , ? , ? , GeomFromEWKT(?) )";
    for(final Stop stop : stops ){
      stop.setRouteId( route.getId() );
      KeyHolder keyHolder = new GeneratedKeyHolder();
      jdbcTemplate.update(
              new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                  PreparedStatement ps = con.prepareStatement(insertIntoSql, new String[]{"id"});
                  ps.setLong(1, stop.getRouteId() );
                  ps.setInt(2, stop.getOrder() );
                  ps.setString(3, stop.getPassenger());
                  ps.setString(4, stop.getPoint().toGeom());
                  return ps;
                }
              }, keyHolder);
      stop.setId(keyHolder.getKey().longValue() );
    }
  }

  @Transactional
  public Location update(Location location) {
    String sql = "UPDATE location SET name = ? , point = GeomFromEWKT(?) WHERE id = ?";
    int update = jdbcTemplate.update(sql, new Object[]{location.getName(), location.getPoint().toGeom(), location.getId()});
    if(update <= 0 ) throw new NotFoundException();
    return location;
  }

  @Transactional
  public Route delete(Integer id){
    Route route = get(id);
    String sql = "DELETE FROM location WHERE id = ?";
    int update = jdbcTemplate.update(sql, new Object[]{id});
    if(update <= 0 ) throw new NotFoundException();
    return route;
  }

  protected Route mountRoute(ResultSet rs) throws SQLException {
    Route r = new Route();
    r.setId( rs.getLong("id") );
    r.setDate( rs.getDate("date") );
    r.setCreatedAt( rs.getDate("created_at") );
    r.setDriver( mountDriver(rs) );
    r.setStartingLocation( mountLocation( rs , "start_" ) );
    r.setEndingLocation(mountLocation(rs, "ending_" ) );
    return r;
  }

  protected Driver mountDriver(ResultSet rs) throws SQLException {
    Driver d = new Driver();
    d.setId( rs.getInt("d_id") );
    d.setName( rs.getString("d_name") );
    d.setLocationId(rs.getInt("d_home_location_id") );
    return d;
  }

  protected Location mountLocation(ResultSet rs, String prefix) throws SQLException {
    Location location = new Location();
    location.setId(rs.getInt(prefix + "id"));
    location.setName(rs.getString(prefix + "name"));
    location.setGeom((PGgeometry) rs.getObject( prefix + "point"));
    location.setPoint(new Point(location.getGeom()));
    return location;
  }


}
