package me.efraimgentil.model;

import java.util.Date;
import java.util.List;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 25/07/16.
 */
public class Route {

  private Long id;
  private Date date;
  private Location startingLocation;
  private Location endingLocation;
  private Driver driver;
  private List<Stop> stops;

  public Route() {  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Route{");
    sb.append("date=").append(date);
    sb.append(", id=").append(id);
    sb.append(", startingLocation=").append(startingLocation);
    sb.append(", endingLocation=").append(endingLocation);
    sb.append(", stops=").append(stops);
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

}
