package me.efraimgentil.service;

import me.efraimgentil.exception.NotFoundException;
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

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
@Service
@Lazy(true)
public class LocationService {

  @Autowired
  JdbcTemplate jdbcTemplate;

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
  public Location create(final Location driver) {
    final String insertIntoSql = "INSERT INTO public.location ( name , point  ) VALUES ( ? , GeomFromEWKT(?) )";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
            new PreparedStatementCreator() {
              public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertIntoSql, new String[]{"id"});
                ps.setString(1, driver.getName());
                ps.setString(2, driver.getPoint().toGeom());
                return ps;
              }
            }, keyHolder);
    driver.setId(keyHolder.getKey().intValue());
    return driver;
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

}
