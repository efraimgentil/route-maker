package me.efraimgentil.service;

import com.fasterxml.jackson.databind.JsonNode;
import me.efraimgentil.exception.NotFoundException;
import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
import me.efraimgentil.model.Route;
import me.efraimgentil.model.Stop;
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
    List<Stop> orderedStops  = orderStops(jsonRoute, route.getStops());
    route.setStops( orderedStops );

    final String insertIntoSql = "INSERT INTO public.route ( date ,  starting_location_id , ending_location_id " +
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
    route.setId(keyHolder.getKey().longValue() );
    saveStops( route );


    return route;
  }

  private void saveStops(Route route) {
    List<Stop> stops = route.getStops();

    final String insertIntoSql = "INSERT INTO public.stop ( date ,  starting_location_id , ending_location_id " +
            ", driver_id , created_at ) VALUES ( ? , ? , ? , ? , now() )";
    for(Stop stop : stops ){
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
      route.setId(keyHolder.getKey().longValue() );
    }
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public List<Location> locations() {
    final List<Location> locations = new ArrayList<>();
    jdbcTemplate.query("SELECT * FROM public.location", new Object[]{}, new RowCallbackHandler() {
      @Override
      public void processRow(ResultSet rs) throws SQLException {
        locations.add(mountLocation(rs));
      }
    });
    return locations;
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public Location get(int id) {
    try {
      Location location = jdbcTemplate.queryForObject("SELECT * FROM public.location WHERE id = ?", new Object[]{id}, new RowMapper<Location>() {
        public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
          return mountLocation(rs);
        }
      });
      return location;
    } catch (EmptyResultDataAccessException erdae) {
      throw new NotFoundException();
    }
  }

  @Transactional
  public Location create(final Location location) {
    final String insertIntoSql = "INSERT INTO public.location ( name , point ) VALUES ( ? , GeomFromEWKT(?)  )";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
            new PreparedStatementCreator() {
              public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertIntoSql, new String[]{"id"});
                ps.setString(1, location.getName());
                ps.setString(2, location.getPoint().toGeom());
                return ps;
              }
            }, keyHolder);
    location.setId(keyHolder.getKey().intValue());
    return location;
  }

  protected Location mountLocation(ResultSet rs) throws SQLException {
    Location location = new Location();
    location.setId(rs.getInt("id"));
    location.setName(rs.getString("name"));
    location.setGeom((PGgeometry) rs.getObject("point"));
    location.setPoint(new Point(location.getGeom()));
    return location;
  }

  @Transactional
  public Location update(Location location) {
    String sql = "UPDATE public.location SET name = ? , point = GeomFromEWKT(?) WHERE id = ?";
    int update = jdbcTemplate.update(sql, new Object[]{location.getName(), location.getPoint().toGeom(), location.getId()});
    if(update <= 0 ) throw new NotFoundException();
    return location;
  }

  @Transactional
  public Location delete(Integer id){
    Location location = get(id);
    String sql = "DELETE FROM public.location WHERE id = ?";
    int update = jdbcTemplate.update(sql, new Object[]{id});
    if(update <= 0 ) throw new NotFoundException();
    return location;
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

}
