package me.efraimgentil.service;

import me.efraimgentil.exception.NotFoundException;
import me.efraimgentil.model.Driver;
import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
@Service
@Lazy(true)
public class DriverService {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public List<Driver> drivers(){
    final List<Driver> drivers = new ArrayList<>();
    jdbcTemplate.query("SELECT * FROM driver", new Object[]{}, new RowCallbackHandler() {
      @Override
      public void processRow(ResultSet rs) throws SQLException {
        drivers.add( mountDriver( rs ) );
      }
    });
    return drivers;
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public Driver get(Integer id) {
    try {
      return jdbcTemplate.queryForObject("SELECT * FROM public.driver WHERE id = ?", new Object[]{id}, new RowMapper<Driver>() {
        public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
          Driver driver = mountDriver(rs);
          driver.setHome( getLocation( driver.getLocationId() ) );
          return driver;
        }
      });
    } catch (EmptyResultDataAccessException erdae) {
      throw new NotFoundException();
    }
  }

  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public Location getLocation(int id) {
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

  @Transactional(propagation = Propagation.REQUIRED )
  public Driver create(final Driver driver){
    driver.setHome(insertAndRetriveLocation(driver));
    final String insertIntoSql = "INSERT INTO public.driver ( name , location_id  ) VALUES ( ? , ?  )";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
            new PreparedStatementCreator() {
              public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertIntoSql, new String[]{"id"});
                ps.setString(1, driver.getName());
                ps.setInt( 2 , driver.getHome().getId() );
                return ps;
              }
            }, keyHolder);
    driver.setId( keyHolder.getKey().intValue() );
    return driver;
  }

  private Location insertAndRetriveLocation(final Driver driver) {
    final int locationId = jdbcTemplate.queryForInt("select nextval('location_id_seq')");
    final String insertIntoSql = "INSERT INTO public.location ( name , point , point_name , private  , id ) VALUES ( ? , GeomFromEWKT(?)  , ? , ? , ?)";
    final Location home = driver.getHome();
    jdbcTemplate.update(insertIntoSql, ( driver.getName() + "'s Home" ) ,  home.getPoint().toGeom() , home.getPointName() , true , locationId  );
    home.setId( locationId );
    return home;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public Driver update(Driver driver) {
    Location home = driver.getHome();
    if(home.getId() == null){
      driver.setHome(insertAndRetriveLocation(driver));
    }else {
      String updateLocation = "UPDATE public.location SET name = ? , point = GeomFromEWKT(?) , point_name = ?  WHERE id = ?";
      int update1 = jdbcTemplate.update(updateLocation, (driver.getName() + "'s Home"), home.getPoint().toGeom(), home.getPointName(), home.getId());
      if(update1 <= 0 ) throw new NotFoundException();
    }
    String updateDriver = "UPDATE public.driver SET name = ? , location_id = ? WHERE id = ?";
    int update = jdbcTemplate.update(updateDriver, driver.getName(), driver.getHome().getId() , driver.getId());
    if( update <= 0) throw new NotFoundException();
    return driver;
  }

  @Transactional(propagation = Propagation.REQUIRED )
  public Driver delete(Integer id){
    Driver driver = get(id);
    jdbcTemplate.update("DELETE FROM driver WHERE id = ?" , id );
    return driver;
  }

  protected Driver mountDriver(ResultSet rs) throws SQLException {
    Driver driver = new Driver();
    driver.setId(rs.getInt("id"));
    driver.setName(rs.getString("name"));
    driver.setLocationId(rs.getInt("location_id"));
    return driver;
  }

  protected Location mountLocation(ResultSet rs) throws SQLException {
    Location location = new Location();
    location.setId(rs.getInt("id"));
    location.setPointName(rs.getString("point_name"));
    location.setName(rs.getString("name"));
    location.setGeom((PGgeometry) rs.getObject("point"));
    location.setPoint(new Point(location.getGeom()));
    return location;
  }

}

