package me.efraimgentil.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;
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

import java.util.List;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class ,  DBUnitConfig.class })
@TestExecutionListeners({
     DependencyInjectionTestExecutionListener.class
   , TransactionalTestExecutionListener.class
   , DbUnitTestExecutionListener.class
})
@DatabaseSetups({
  @DatabaseSetup(  value = "classpath:routeServiceDatabase.xml"  ,type =  DatabaseOperation.CLEAN_INSERT)
})
@DatabaseTearDown(value={"classpath:routeServiceDatabaseTearDown.xml"}, type = DatabaseOperation.DELETE )
@TransactionConfiguration
public class RouteServiceIT {

  @Autowired
  RouteService service;

  @Rollback(true)
  @Transactional
  @Test
  public void doesInsertANewLoctation(){
    Route route = new Route();
    route.setDate( new Date() );
    route.setStartingLocation( new Location( 2 ));
    route.setEndingLocation( new Location(3 ));
    route.addStep( new Stop( 1 ,"Efras" , new Point(-3.743499979657115 , -38.53038311004639 ) ) );
    route.addStep( new Stop( 0 , "Efras" , new Point(-3.742129617710173 , -38.54128360748291 ) ) );
    route.setDriver( new Driver(1) );
    route = service.create( route );

    assertNotNull( route.getId() );
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
