package me.efraimgentil.controller;

import me.efraimgentil.model.Driver;
import me.efraimgentil.model.Location;
import me.efraimgentil.model.Route;
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

  @Autowired RouteValidator validator;
  @Autowired RouteService routeService;

  @InitBinder
  public void initBinder(WebDataBinder binder){
    binder.setValidator( validator );
  }


  @RequestMapping(value = { "/suggest-driver" , "/suggest-driver/" } , method =  RequestMethod.POST)
  public Driver sugestDriver( @RequestBody Route route ){
    return routeService.suggestADriver( route );
  }

  @RequestMapping(value = { "/" , "" } , method =  RequestMethod.GET)
  public List<Route> route(){
    return routeService.routes();
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.GET)
  public Route route(@PathVariable("id") Integer id ){
    return routeService.get(id);
  }

  @RequestMapping(value = { "/" , "" } , method = RequestMethod.POST )
  public Route insert( @RequestBody @Validated  Route route , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return routeService.create(route);
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.PUT )
  public Location update( @PathVariable("id") Integer id,  @RequestBody @Validated Location driver , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return routeService.update(driver);
  }

  @RequestMapping(value = { "/{id}" , "/{id}/" } , method = RequestMethod.DELETE )
  public Route delete( @PathVariable("id") Integer id ){
    return routeService.delete(id);
  }

}
