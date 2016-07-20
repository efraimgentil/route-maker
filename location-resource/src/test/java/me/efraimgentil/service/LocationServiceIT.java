package me.efraimgentil.service;

import me.efraimgentil.config.DatabaseConfig;
import me.efraimgentil.config.SpringConfig;
import me.efraimgentil.exception.NotFoundException;
import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class , DatabaseConfig.class })
@TransactionConfiguration
public class LocationServiceIT {


  @Autowired
  DataSource dataSource;

  @Autowired
  LocationService locationService;

/*  @Test
  public void does() throws SQLException {
    try(Connection conn = dataSource.getConnection()){
      ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM public.location");
      while(rs.next()){
        System.out.println( rs.getObject("point") );
        PGgeometry geom = (PGgeometry)rs.getObject("point");
        System.out.println(geom.toString());
        System.out.println(geom.getValue());
        System.out.println(geom.getType());
        System.out.println(geom.getGeoType());
        System.out.println(geom.getGeometry());
      }
    }
  }*/

  @Rollback(true)
  @Transactional
  @Test
  public void doesInsertANewLocation(){
    Location l = new Location();
    l.setName("Home 1111");
    l.setPoint( new Point( 5.0 , 6.0 ) );

    Location location = locationService.create(l);

    assertNotNull( location.getId() );
  }

  @Rollback(true)
  @Transactional
  @Test
  public void doesUpdateAnExistingLocation(){
    Location l = new Location();
    l.setId( 1 );
    l.setName("Home 2222");
    l.setPoint( new Point( 5.0 , 6.0 ) );

    Location location = locationService.update(l);
  }

  @Test
  public void doesQueryAllLocations(){
    List<Location> locations = locationService.locations();

    assertFalse( locations.isEmpty() );
    for( Location l : locations){
      assertNotNull(l.getId() );
      assertNotNull(l.getName() );
      assertNotNull(l.getGeom() );
      assertNotNull(l.getPoint() );
    }
  }

  @Test
  public void doesReturnTheLocationOfTheSpecifiedId(){
    Location location = locationService.get(1);

    assertNotNull(location);
  }

  @Test(expected = NotFoundException.class)
  public void doesThrowNotFoundExceptionWhenThereIsNoLocationWithTheSpecifiedId(){
    Location location = locationService.get(99999);
    assertNotNull(location);
  }

}
