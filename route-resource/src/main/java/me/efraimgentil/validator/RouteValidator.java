package me.efraimgentil.validator;

import me.efraimgentil.model.Location;
import me.efraimgentil.model.Point;
import me.efraimgentil.model.Route;
import me.efraimgentil.model.Stop;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;
import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
@Component
@Lazy(true)
public class RouteValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return  Route.class.equals( clazz );
  }

  @Override
  public void validate(Object target, Errors errors) {
    Route route = (Route) target;
    validateDate( route , errors );
    validateStartingLocation(route, errors);
    validateEndingLocation( route , errors );
    validateStops( route , errors );
    validateDriver( route , errors );
  }

  protected void validateDate(Route route, Errors errors) {
    if(route.getDate() == null ){
      errors.rejectValue( "date" , "field.required");
    }else if ( new Date().after( route.getDate() ) ){
      errors.rejectValue( "date" , "field.invalid.date");
    }
  }

  protected void validateDriver(Route route, Errors errors) {
    if(route.getDriver() == null || route.getDriver().getId() == null ){
      errors.rejectValue( "driver" , "field.required");
    }
  }

  protected void validateStops(Route route, Errors errors) {
    List<Stop> stops = route.getStops();
    if(stops == null || stops.isEmpty()){
      errors.rejectValue("stops" , "field.required.stop");
    }else{
      for(int i = 0 ; i < stops.size() ; i++ ){
        Stop stop = stops.get(i);
        if(!StringUtils.hasText( stop.getPassenger() )) {
          errors.rejectValue("stops." + i + ".passenger" , "field.required");
        }else if( stop.getPoint() == null ){
          errors.rejectValue("stops." + i + ".point" , "field.required.stop.point");
        }else if( stop.getPoint().getLat() == null || stop.getPoint().getLng() == null ){
          errors.rejectValue("stops." + i + ".point" , "field.invalid.point");
        }
      }
    }
  }

  protected void validateStartingLocation(Route route, Errors errors) {
    validateLocation("startingLocation", route.getStartingLocation(), errors);
  }

  protected void validateEndingLocation(Route route, Errors errors) {
    validateLocation( "endingLocation" , route.getEndingLocation() , errors );
  }

  protected void validateLocation(String fieldName , Location location , Errors errors ){
    if( location == null ){
      errors.rejectValue( fieldName , "field.required");
    }else if ( location.getPoint() == null ){
      errors.rejectValue( fieldName , "field.invalid.point");
    }else if( location.getPoint().getLat() == null
            || location.getPoint().getLng() == null ){
      errors.rejectValue( fieldName , "field.invalid.point");
    }
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
