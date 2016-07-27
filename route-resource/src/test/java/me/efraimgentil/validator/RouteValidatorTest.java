package me.efraimgentil.validator;

import me.efraimgentil.model.*;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 11/07/16.
 */
public class RouteValidatorTest {

  private RouteValidator validator;
  private Errors errors;

  @Before
  public void setUp(){
    validator = new RouteValidator();
    errors = mock( Errors.class );
  }

  @Test
  public void doesReturnTrueWhenTheTypeIsAnDriver(){
    assertTrue("Should only support Route.class" , validator.supports( Route.class ) );
  }

  @Test
  public void doesReturnFalseWhenTheTypeIsAnDriver(){
    assertFalse("Should only support Route.class", validator.supports(Location.class));
  }

  @Test
  public void doesRejectValueWhenStartingLocationIsNull(){
    Route route = new Route();
    route.setStartingLocation( null );

    validator.validateStartingLocation( route , errors );

    verify(errors, times(1)).rejectValue( "startingLocation" , "field.required"  );
  }

  @Test
  public void doesRejectValueWhenStartingLocationDoesntHaveAPointValue(){
    Route route = new Route();
    route.setStartingLocation(  new Location() );

    validator.validateStartingLocation( route , errors );

    verify( errors , times(1) ).rejectValue( "startingLocation" , "field.invalid.point"  );
  }

  @Test
  public void doesRejectValueWhenStartingLocationDoesHaveAPointButIsInvalid(){
    Route route = new Route();
    Location location = new Location();
    location.setPoint( new Point( null , 0.1 ) );
    route.setStartingLocation(  location );

    validator.validateStartingLocation( route , errors );

    verify( errors , times(1) ).rejectValue( "startingLocation" , "field.invalid.point"  );
  }

  @Test
  public void doesRejectValueWhenEndingLocationIsNull(){
    Route route = new Route();
    route.setEndingLocation(null);

    validator.validateEndingLocation(route, errors);

    verify(errors, times(1)).rejectValue( "endingLocation" , "field.required"  );
  }

  @Test
  public void doesRejectValueWhenEndingLocationDoesntHaveAPointValue(){
    Route route = new Route();
    route.setEndingLocation(new Location());

    validator.validateEndingLocation(route, errors);

    verify( errors , times(1) ).rejectValue( "endingLocation" , "field.invalid.point"  );
  }

  @Test
  public void doesRejectValueWhenEndingLocationDoesHaveAPointButIsInvalid(){
    Route route = new Route();
    Location location = new Location();
    location.setPoint( new Point( null , 0.1 ) );
    route.setEndingLocation(location);

    validator.validateEndingLocation(route, errors);

    verify( errors , times(1) ).rejectValue( "endingLocation" , "field.invalid.point"  );
  }

  @Test
  public void doesRejectValueWhenStopsIsNull(){
    Route route = new Route();
    route.setStops( null );

    validator.validateStops(route, errors);

    verify( errors , times(1) ).rejectValue( "stops" , "field.required.stop"  );
  }

  @Test
  public void doesRejectValueWhenStopsIsEmpty(){
    Route route = new Route();
    route.setStops( new ArrayList<>() );

    validator.validateStops( route , errors );

    verify( errors , times(1) ).rejectValue( "stops" , "field.required.stop"  );
  }

  @Test
  public void doesRejectValueWhenTheSecondStopHasNoPassengerValue(){
    Route route = new Route();
    List<Stop> stops =  new ArrayList<>();
    stops.add( new Stop("Hello" , new Point(1.0 , 0.1)));
    stops.add( new Stop("" , new Point(1.0 , 0.1)));
    route.setStops(  stops );

    validator.validateStops( route , errors );

    verify( errors , times(1) ).rejectValue( "stops.1.passenger" , "field.required"  );
  }

  @Test
  public void doesRejectValueWhenTheFirstStopHasNoPassengerValue(){
    Route route = new Route();
    List<Stop> stops =  new ArrayList<>();
    stops.add( new Stop("" , new Point(1.0 , 0.1)));
    stops.add( new Stop("Hello" , new Point(1.0 , 0.1)));
    route.setStops(  stops );

    validator.validateStops( route , errors );

    verify( errors , times(1) ).rejectValue( "stops.0.passenger" , "field.required"  );
  }

  @Test
  public void doesRejectValueWhenTheFirstStopHasNoPointValue(){
    Route route = new Route();
    List<Stop> stops =  new ArrayList<>();
    stops.add( new Stop("Tes" ,  null ));
    stops.add( new Stop("Hello" , new Point(1.0 , 0.1)));
    route.setStops(  stops );

    validator.validateStops( route , errors );

    verify( errors , times(1) ).rejectValue( "stops.0.point" , "field.required.stop.point" );
  }

  @Test
  public void doesRejectValueWhenTheFirstStopHasAPointWithInvalidValue(){
    Route route = new Route();
    List<Stop> stops =  new ArrayList<>();
    stops.add( new Stop("Hello" , new Point(1.0 , 0.1)) );
    stops.add( new Stop("Tes" ,  new Point( 0.1 , null ) ) );
    route.setStops(  stops );

    validator.validateStops( route , errors );

    verify( errors , times(1) ).rejectValue( "stops.1.point" , "field.invalid.point" );
  }

  @Test
  public void doesRejectValueWhenThereIsNoDriverValue(){
    Route route = new Route();
    route.setDriver(null);

    validator.validateDriver(route, errors);

    verify( errors , times(1) ).rejectValue( "driver" , "field.required" );
  }

  @Test
  public void doesRejectValueWhenDriverHasNoIdValue(){
    Route route = new Route();
    route.setDriver( new Driver() );

    validator.validateDriver( route , errors );

    verify( errors , times(1) ).rejectValue( "driver" , "field.required" );
  }

  @Test
  public void doesRejectValueWhenThereIsNoDateValue(){
    Route route = new Route();
    route.setDate( null );

    validator.validateDate(route, errors);

    verify( errors , times(1) ).rejectValue( "date" , "field.required" );
  }

  @Test
  public void doesRejectValueWhenTheDateValueIsInThePast(){
    Route route = new Route();
    route.setDate(  new DateTime().minusDays(1).toDate() );

    validator.validateDate( route , errors );

    verify( errors , times(1) ).rejectValue( "date" , "field.invalid.date" );
  }



}
