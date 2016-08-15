package me.efraimgentil.service;

import com.fasterxml.jackson.databind.JsonNode;
import me.efraimgentil.config.SpringConfig;
import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
import me.efraimgentil.model.Route;
import me.efraimgentil.model.Stop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 27/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class  })
public class GoogleDirectionServiceIT {

  @Autowired GoogleDirectionService service;

  private Route mountTestRoute(){
    Route route = new Route();
    route.setStartingLocation( new Location("Extra" , new Point( -3.748777994300508 , -38.523672223091125 )));
    route.setEndingLocation(new Location("Jornal O Povo", new Point(-3.7392711214378163, -38.52481484413147 )));

    return route;
  }

  @Test
  public void doesCallGoogleDirectionToMountARoute(){
    JsonNode jsonNode = service.callGoogleService(mountTestRoute());

    System.out.println("jsonNode = " + jsonNode);
  }


  @Test
  public void doesCallGoogleDirectionToMountARouteWithStops(){
    Route route = mountTestRoute();
    route.addStep( new Stop("Efras" , new Point(-3.743499979657115 , -38.53038311004639 ) ) );
    route.addStep( new Stop("Efras" , new Point(-3.742129617710173 , -38.54128360748291 ) ) );
    JsonNode jsonNode = service.callGoogleService( route );

    System.out.println("jsonNode = " + jsonNode);
  }


}
