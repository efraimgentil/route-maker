package me.efraimgentil.service;

import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;

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

  public List<Location> locations(){
    List<Location> locations = new ArrayList<>();
    jdbcTemplate.query("SELECT * FROM public.location", new Object[]{}, new RowCallbackHandler() {
      @Override
      public void processRow(ResultSet rs) throws SQLException {
        Location location = new Location();
        location.setId(rs.getInt("id"));
        location.setName(rs.getString("name"));
        location.setGeom((PGgeometry) rs.getObject("point"));
        location.setPoint( new Point( location.getGeom() ));
      }
    });
    return locations;
  }

 /* public Location get(Integer id) {
    int indexOf = drivers.indexOf(new Location( id ));
    if(indexOf < 0) throw new NotFoundException();
    return drivers.get(indexOf );
  }*/

  public Location create(Location driver){
    jdbcTemplate.update("INSERT INTO public.location ( name , point  ) VALUES ( ? , GeomFromEWKT(?) )"
            , driver.getName(), driver.getPoint().toGeom() );
    return driver;
  }

  /*public Location update(Location driver) {
    int indexOf = drivers.indexOf(driver);
    if(indexOf < 0) throw new NotFoundException();
    drivers.set( indexOf , driver );
    return driver;
  }

  public Location delete(Integer id){
    int indexOf = drivers.indexOf(new Location( id ));
    if(indexOf < 0) throw new NotFoundException();
    Location driver = drivers.get(indexOf);
    drivers.remove( indexOf );
    return driver;
  }*/


}
