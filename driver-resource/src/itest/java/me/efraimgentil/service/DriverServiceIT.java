package me.efraimgentil.service;

import me.efraimgentil.config.DatabaseConfig;
import me.efraimgentil.config.SpringConfig;
import me.efraimgentil.model.Driver;
import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
import me.efraimgentil.service.DriverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 20/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class, DatabaseConfig.class})
@TransactionConfiguration
public class DriverServiceIT {

  @Autowired
  DriverService service;


  @Rollback(true)
  @Transactional
  @Test
  public void doesInsertDriverAndLocation() {
    Driver d = new Driver();
    d.setName("Efras");
    Location l = new Location();
    l.setPointName("Well this should do");
    l.setPoint(new Point(1, 1));
    d.setHome(l);

    Driver driver = service.create(d);

    assertNotNull( driver.getId() );
    assertNotNull( driver.getHome().getId() );
  }

  @Rollback(true)
  @Transactional
  @Test
  public void doesDriverDriverAndLocation() {
    Driver d = new Driver();
    d.setId( 1 );
    d.setName("Driver Efras");
    Location l = new Location();
    l.setId( 17 );
    l.setPointName("My New Point Name");
    l.setPoint(new Point(0, 0));
    d.setHome(l);

    Driver driver = service.update(d);

    assertNotNull( driver.getId() );
    assertNotNull( driver.getHome().getId() );
  }

}
