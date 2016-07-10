package me.efraimgentil.validator;

import me.efraimgentil.model.Driver;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
@Component
public class DriverValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return  Driver.class.equals( clazz );
  }

  @Override
  public void validate(Object target, Errors errors) {
    Driver driver = (Driver) target;
    validateName( driver , errors );
  }

  public void validateName( Driver target , Errors errors  ){
    ValidationUtils.rejectIfEmpty( errors , "name" , "field.required" );
  }

}
