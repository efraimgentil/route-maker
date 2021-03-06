package me.efraimgentil.validator;

import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 11/07/16.
 */
public class LocationValidatorTest {

  private LocationValidator validator;
  private Errors errors;

  @Before
  public void setUp(){
    validator = new LocationValidator();
    errors = mock( Errors.class );
  }

  @Test
  public void doesReturnTrueWhenTheTypeIsAnDriver(){
    assertTrue("Should only support Location.class" , validator.supports( Location.class ) );
  }

  @Test
  public void doesRejectTheValueWhenTheLocationNameIsNull(){
    Location location = new Location();
    location.setName(null);

    validator.validateName( location , errors  );

    verify( errors , times(1) ).rejectValue( "name" , "field.required" , null , null );
  }

  @Test
  public void doesRejectTheValueWhenTheLocationNameIsEmpty(){
    Location driver = new Location();
    driver.setName("");

    validator.validateName(driver, errors);

    verify(errors, times(1)).rejectValue( "name" , "field.required" , null , null );
  }

  @Test
  public void doesRejectTheValueWhenTheLocationPointIsNull(){
    Location location = new Location();
    location.setName(null);

    validator.validatePoint(location, errors);

    verify( errors , times(1) ).rejectValue( "point" , "field.required"  );
  }

  @Test
  public void doesRejectTheValueWhenTheLocationPointIsInvalid(){
    Location location = new Location();
    location.setName(null);
    location.setPoint( new Point( null , null ) );

    validator.validatePoint( location , errors  );

    verify( errors , times(1) ).rejectValue( "point" , "field.invalid.point"  );
  }

}
