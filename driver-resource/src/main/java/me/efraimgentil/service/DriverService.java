package me.efraimgentil.service;

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

  public Driver insert(Driver driver){
    driver.setId( ids.incrementAndGet() );
    drivers.add(driver);
    return driver;
  }

}
