package me.efraimgentil.controller;

import me.efraimgentil.model.Driver;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 09/07/16.
 */
@RestController
@RequestMapping(value = { "/" })
public class DriverController {



  @RequestMapping(value = { "/" , "" })
  public List<Driver> drivers(){
    return Collections.emptyList();
  }

  public Driver insert( @RequestBody Driver driver){


    return driver;
  }

}
