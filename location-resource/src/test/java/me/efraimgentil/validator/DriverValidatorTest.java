package me.efraimgentil.validator;

import me.efraimgentil.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 11/07/16.
 */
public class DriverValidatorTest {

  private LocationValidator validator;
  private Errors errors;

  @Before
  public void setUp(){
    validator = new LocationValidator();
    errors = mock( Errors.class );
  }

  @Test
  public void doesReturnTrueWhenTheTypeIsAnDriver(){
    assertTrue("Should only support Driver.class" , validator.supports( Location.class ) );
  }

  @Test
  public void doesRejectTheValueWhenTheDriverNameIsNull(){
    Location driver = new Location();
    driver.setName(null );

    validator.validateName( driver , errors  );

    verify( errors , times(1) ).rejectValue( "name" , "field.required" , null , null );
  }

  @Test
  public void doesRejectTheValueWhenTheDriverNameIsEmpty(){
    Location driver = new Location();
    driver.setName("");

    validator.validateName( driver , errors  );

    verify( errors , times(1) ).rejectValue( "name" , "field.required" , null , null );
  }


}
