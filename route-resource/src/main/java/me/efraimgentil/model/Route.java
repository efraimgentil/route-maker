package me.efraimgentil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 25/07/16.
 */
public class Route {

  private Long id;
  private Date date;
  private Date createdAt;
  private Location startingLocation;
  private Location endingLocation;
  private Driver driver;
  private List<Stop> stops = new ArrayList<>();
  private String routeJson;

  private boolean routeMounted;

  public Route() {  }

  @JsonIgnore
  public void addStep(Stop stop){
    stops.add( stop );
  }

  @JsonIgnore
  public Stop getLastStop(){
    return  stops.get( stops.size() - 1 );
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Route{");
    sb.append("startingLocation=").append(startingLocation);
    sb.append(", id=").append(id);
    sb.append(", endingLocation=").append(endingLocation);
    sb.append(", driver=").append(driver);
    sb.append(", date=").append(date);
    sb.append(", createdAt=").append(createdAt);
    sb.append('}');
    return sb.toString();
  }

  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public Location getEndingLocation() {
    return endingLocation;
  }
  public void setEndingLocation(Location endingLocation) {
    this.endingLocation = endingLocation;
  }
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Location getStartingLocation() {
    return startingLocation;
  }
  public void setStartingLocation(Location startingLocation) {
    this.startingLocation = startingLocation;
  }
  public List<Stop> getStops() {
    return stops;
  }
  public void setStops(List<Stop> stops) {
    this.stops = stops;
  }
  public Driver getDriver() {
    return driver;
  }
  public void setDriver(Driver driver) {
    this.driver = driver;
  }
  public Date getCreatedAt() {
    return createdAt;
  }
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
  public boolean isRouteMounted() {
    return routeMounted;
  }
  public void setRouteMounted(boolean routeMounted) {
    this.routeMounted = routeMounted;
  }

  public String getRouteJson() {
    return routeJson;
  }

  public void setRouteJson(String routeJson) {
    this.routeJson = routeJson;
  }
}
