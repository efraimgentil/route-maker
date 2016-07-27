package me.efraimgentil.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 09/07/16.
 */
@JsonIgnoreProperties(ignoreUnknown =  true)
public class Driver {

  private Integer id;
  private String name;
  private Integer locationId;
  private Location home;

  public Driver() {
  }

  public Driver(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Driver{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Driver driver = (Driver) o;
    if (id != null ? !id.equals(driver.id) : driver.id != null) return false;
    return true;
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public Location getHome() {
    return home;
  }

  public void setHome(Location home) {
    this.home = home;
  }

  public Integer getLocationId() {
    return locationId;
  }

  public void setLocationId(Integer locationId) {
    this.locationId = locationId;
  }
}
