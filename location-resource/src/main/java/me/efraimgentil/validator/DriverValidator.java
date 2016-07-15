package me.efraimgentil.validator;

import me.efraimgentil.model.Location;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
@Component
@Lazy(true)
public class DriverValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return  Location.class.equals( clazz );
  }

  @Override
  public void validate(Object target, Errors errors) {
    Location driver = (Location) target;
    validateName( driver , errors );
  }

  public void validateName( Location target , Errors errors  ){
    ValidationUtils.rejectIfEmpty( errors , "name" , "field.required" );
  }

}
