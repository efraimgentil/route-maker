package me.efraimgentil.service;

import me.efraimgentil.exception.NotFoundException;
import me.efraimgentil.model.Location;
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
public class LocationService {

  private AtomicInteger ids = new AtomicInteger(0);
  private static List<Location> drivers = new ArrayList<>();

  public List<Location> drivers(){
    return new ArrayList<>( drivers );
  }

  public Location get(Integer id) {
    int indexOf = drivers.indexOf(new Location( id ));
    if(indexOf < 0) throw new NotFoundException();
    return drivers.get(indexOf );
  }

  public Location create(Location driver){
    driver.setId( ids.incrementAndGet() );
    drivers.add(driver);
    return driver;
  }

  public Location update(Location driver) {
    int indexOf = drivers.indexOf(driver);
    if(indexOf < 0) throw new NotFoundException();
    drivers.set( indexOf , driver );
    return driver;
  }

  public Location delete(Integer id){
    int indexOf = drivers.indexOf(new Location( id ));
    if(indexOf < 0) throw new NotFoundException();
    Location driver = drivers.get(indexOf);
    drivers.remove( indexOf );
    return driver;
  }


}
