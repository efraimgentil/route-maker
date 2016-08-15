package me.efraimgentil.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;
import me.efraimgentil.DBUnitConfig;
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
    route.setDate(new Date());
    route.setStartingLocation(new Location(2));
    route.setEndingLocation( new Location(3 ));
    route.addStep( new Stop( 1 ,"Efras" , new Point(-3.743499979657115 , -38.53038311004639 ) ) );
    route.addStep( new Stop( 0 , "Efras" , new Point(-3.742129617710173 , -38.54128360748291 ) ) );
    route.setDriver( new Driver(1) );
    route = service.create( route );

    assertNotNull( route.getId() );
  }

  @Test
  public void doesQueryAllRoutesWithStartingEndingAndDriverInformation(){
    List<Route> routes = service.routes();

    assertNotNull(routes);
    assertFalse(routes.isEmpty());
    for( Route r : routes ){
      assertNotNull(r.getStartingLocation());
      assertEquals(new Integer(3), r.getStartingLocation().getId());
      assertNotNull( r.getStartingLocation().getPoint() );
      assertNotNull( r.getEndingLocation() );
      assertEquals( new Integer(2 ) , r.getEndingLocation().getId() );
      assertNotNull( r.getEndingLocation().getPoint() );
      assertNotNull( r.getDriver() );
      assertEquals( new Integer( 1 ) , r.getDriver().getId() );
      assertNotNull( r.getDriver().getName() ) ;
    }
  }

  @Test
  public void doesReturnTheRouteOfTheSpecifiedId(){
    Route r = service.get(1);

    assertNotNull(r);
    assertNotNull(r.getStartingLocation());
    assertEquals(new Integer(3), r.getStartingLocation().getId());
    assertNotNull( r.getStartingLocation().getPoint() );
    assertNotNull( r.getEndingLocation() );
    assertEquals( new Integer(2 ) , r.getEndingLocation().getId() );
    assertNotNull( r.getEndingLocation().getPoint() );
    assertNotNull( r.getDriver() );
    assertEquals( new Integer( 1 ) , r.getDriver().getId() );
    assertNotNull( r.getDriver().getName() ) ;
  }
  
  @Rollback(true)
  @Test
  @Transactional
  public void doesSuccesfullyDeleteARoute(){
    Route r = service.get(1L);

    assertNotNull(r);
  }

  @Test(expected = NotFoundException.class)
  public void doesThrowNotFoundExceptionWhenThereIsNoLocationWithTheSpecifiedId(){
    Route route = service.get(99999);
  }


  @Test
  public void doesReturnTheDriverWhoLivesNearestThePoint(){
    Point p = new Point(-3.7604794425063166 , -38.51583480834961 );
    Driver driver = service.nearestDriver(p);

    assertNotNull( driver );
    assertEquals("Should have been the driver 2 Mike", new Integer(2) , driver.getId()  );
  }
/*  @Rollback(true)
  @Transactional
  @Test
  public void doesUpdateAnExistingLocation(){
    Location l = new Location();
    l.setId(1);
    l.setName("Home 2222");
    l.setPoint( new Point( 5.0 , 6.0 ) );

    Location location = service.update(l);
  }*/



}
