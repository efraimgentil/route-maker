package me.efraimgentil.controller;

import me.efraimgentil.model.Driver;
import me.efraimgentil.validator.DriverValidator;
import me.efraimgentil.validator.InvalidaModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 09/07/16.
 */
@RestController
@RequestMapping(value = { "/" })
public class DriverController  {

  @Autowired
  DriverValidator validator;

  @InitBinder
  public void initBinder(WebDataBinder binder){
    binder.setValidator( validator );
  }

  @RequestMapping(value = { "/" , "" })
  public List<Driver> drivers(){
    return Collections.emptyList();
  }

  @RequestMapping(value = { "/" , "" } , method = RequestMethod.POST )
  public Driver insert( @RequestBody @Validated Driver driver , BindingResult result){
    if(result.hasErrors()) throw new InvalidaModelException( result );
    return driver;
  }

}
