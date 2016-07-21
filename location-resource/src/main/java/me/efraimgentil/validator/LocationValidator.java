package me.efraimgentil.validator;

import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
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
public class LocationValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return  Location.class.equals( clazz );
  }

  @Override
  public void validate(Object target, Errors errors) {
    Location location = (Location) target;
    validateName( location , errors );
    validatePoint( location , errors );
  }

  protected void validatePoint(Location target, Errors errors) {
    Point point = target.getPoint();
    if(point == null){
      errors.rejectValue("point" , "field.required");
    }else if( point.getLat() == null || point.getLng() == null ){
      errors.rejectValue("point" , "field.invalid.point");
    }
  }

  protected void validateName( Location target , Errors errors  ){
    ValidationUtils.rejectIfEmpty( errors , "name" , "field.required" );
  }

}
