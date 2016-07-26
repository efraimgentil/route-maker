package me.efraimgentil.controller;

import me.efraimgentil.model.Location;
import me.efraimgentil.service.RouteService;
import me.efraimgentil.validator.RouteValidator;
import me.efraimgentil.exception.InvalidaModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 09/07/16.
 */
@RestController
@RequestMapping(value = { "/" })
public class RouteController {

  @Autowired
  RouteValidator validator;
  @Autowired
  RouteService locationService;

  @InitBinder
  public void initBinder(WebDataBinder binder){
    binder.setValidator( validator );
  }

  @RequestMapping(value = { "/" , "" } , method =  RequestMethod.GET)
  public List<Location> locations(){
    return locationService.locations();
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.GET)
  public Location location(@PathVariable("id") Integer id ){
    return locationService.get(id);
  }

  @RequestMapping(value = { "/" , "" } , method = RequestMethod.POST )
  public Location insert( @RequestBody @Validated Location location , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return locationService.create(location);
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.PUT )
  public Location update( @PathVariable("id") Integer id,  @RequestBody @Validated Location driver , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return locationService.update(driver);
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.DELETE )
  public Location delete( @PathVariable("id") Integer id ){
    return locationService.delete(id);
  }

}
