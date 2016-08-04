package me.efraimgentil.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseSetups;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import me.efraimgentil.DBUnitConfig;
import me.efraimgentil.config.DatabaseConfig;
import me.efraimgentil.config.SpringConfig;
import me.efraimgentil.exception.NotFoundException;
import me.efraimgentil.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class , DatabaseConfig.class , DBUnitConfig.class })
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class
        , TransactionalTestExecutionListener.class
        ,DbUnitTestExecutionListener.class
})
@TransactionConfiguration
@DatabaseSetups({
  @DatabaseSetup(  value = "classpath:routeServiceDatabase.xml" )
})
public class RouteServiceIT {

  @Autowired
  DataSource dataSource;

  @Autowired
  RouteService service;


  @Test
  public void does(){
    System.out.println( service.get(1) ) ;
  }
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
    Route route = new Route();
    route.setDate( new Date() );
    route.setStartingLocation( new Location( 1, "Extra" , new Point( -3.748777994300508 , -38.523672223091125 )));
    route.setEndingLocation(new Location(2, "Jornal O Povo", new Point(-3.7392711214378163, -38.52481484413147 )));
    route.addStep( new Stop("Efras" , new Point(-3.743499979657115 , -38.53038311004639 ) ) );
    route.addStep( new Stop("Efras" , new Point(-3.742129617710173 , -38.54128360748291 ) ) );
    route.setDriver( new Driver(1) );
    route = service.create( route );

    assertNotNull(route.getId() );
  }

  @Rollback(true)
  @Transactional
  @Test
  public void doesUpdateAnExistingLocation(){
    Location l = new Location();
    l.setId( 1 );
    l.setName("Home 2222");
    l.setPoint( new Point( 5.0 , 6.0 ) );

    Location location = service.update(l);
  }

  @Test
  public void doesQueryAllLocations(){
    List<Location> locations = service.locations();

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
    Location location = service.get(1);

    assertNotNull(location);
  }

  @Test(expected = NotFoundException.class)
  public void doesThrowNotFoundExceptionWhenThereIsNoLocationWithTheSpecifiedId(){
    Location location = service.get(99999);
    assertNotNull(location);
  }

}
