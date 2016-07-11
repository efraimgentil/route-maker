package me.efraimgentil.validator;

import me.efraimgentil.model.Driver;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.Errors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 11/07/16.
 */
public class DriverValidatorTest {

  private DriverValidator validator;
  private Errors errors;

  @Before
  public void setUp(){
    validator = new DriverValidator();
    errors = mock( Errors.class );
  }

  @Test
  public void doesReturnTrueWhenTheTypeIsAnDriver(){
    assertTrue("Should only support Driver.class" , validator.supports( Driver.class ) );
  }

  @Test
  public void doesRejectTheValueWhenTheDriverNameIsNull(){
    Driver driver = new Driver();
    driver.setName(null );

    validator.validateName( driver , errors  );

    verify( errors , times(1) ).rejectValue( "name" , "field.required" , null , null );
  }

  @Test
  public void doesRejectTheValueWhenTheDriverNameIsEmpty(){
    Driver driver = new Driver();
    driver.setName("");

    validator.validateName( driver , errors  );

    verify( errors , times(1) ).rejectValue( "name" , "field.required" , null , null );
  }


}
