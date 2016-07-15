package me.efraimgentil.controller;

import me.efraimgentil.model.Location;
import me.efraimgentil.service.LocationService;
import me.efraimgentil.validator.DriverValidator;
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
public class LocationController {

  @Autowired
  DriverValidator validator;
  @Autowired
  LocationService driverService;

  @InitBinder
  public void initBinder(WebDataBinder binder){
    binder.setValidator( validator );

  }

  @RequestMapping(value = { "/" , "" } , method =  RequestMethod.GET)
  public List<Location> drivers(){
    return null;
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.GET)
  public Location driver(@PathVariable("id") Integer id ){

    return null; //driverService.get(id);
  }

  @RequestMapping(value = { "/" , "" } , method = RequestMethod.POST )
  public Location insert( @RequestBody @Validated Location driver , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return driverService.create(driver);
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.PUT )
  public Location update( @PathVariable("id") Integer id,  @RequestBody @Validated Location driver , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return null; //driverService.update(driver);
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.DELETE )
  public Location delete( @PathVariable("id") Integer id ){

    return null;// driverService.delete(id);
  }


}
