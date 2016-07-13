package me.efraimgentil.controller;

import me.efraimgentil.model.Driver;
import me.efraimgentil.service.DriverService;
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
public class DriverController  {

  @Autowired
  DriverValidator validator;
  @Autowired
  DriverService driverService;

  @InitBinder
  public void initBinder(WebDataBinder binder){
    binder.setValidator( validator );
  }

  @RequestMapping(value = { "/" , "" } , method =  RequestMethod.GET)
  public List<Driver> drivers(){
    return driverService.drivers();
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.GET)
  public Driver driver(@PathVariable("id") Integer id ){

    return driverService.get(id);
  }

  @RequestMapping(value = { "/" , "" } , method = RequestMethod.POST )
  public Driver insert( @RequestBody @Validated Driver driver , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return driverService.create(driver);
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.PUT )
  public Driver update( @PathVariable("id") Integer id,  @RequestBody @Validated Driver driver , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return driverService.update(driver);
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.DELETE )
  public Driver delete( @PathVariable("id") Integer id ){
    return driverService.delete(id);
  }


}
