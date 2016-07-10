package me.efraimgentil.service;

import me.efraimgentil.exception.NotFoundException;
import me.efraimgentil.model.Driver;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */
@Service
@Lazy(true)
public class DriverService {

  private AtomicInteger ids = new AtomicInteger(0);
  private static List<Driver> drivers = new ArrayList<>();

  public List<Driver> drivers(){
    return new ArrayList<>( drivers );
  }

  public Driver create(Driver driver){
    driver.setId( ids.incrementAndGet() );
    drivers.add(driver);
    return driver;
  }

  public Driver update(Driver driver) {
    int indexOf = drivers.indexOf(driver);
    if(indexOf < 0) throw new NotFoundException();
    drivers.set( indexOf , driver );
    return driver;
  }

  public Driver delete(Integer id){
    int indexOf = drivers.indexOf(new Driver( id ));
    if(indexOf < 0) throw new NotFoundException();
    Driver driver = drivers.get(indexOf);
    drivers.remove( indexOf );
    return driver;
  }


}
