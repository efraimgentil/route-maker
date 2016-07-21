package me.efraimgentil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.postgis.PGgeometry;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 09/07/16.
 */
public class Location {

  private Integer id;
  private String name;
  private String pointName;
  private Point point;
  private boolean privateLocation;

  @JsonIgnore
  private PGgeometry geom;

  public Location() {
  }

  public Location(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Location{");
    sb.append(", id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", pointName='").append(pointName).append('\'');
    sb.append(", point=").append(point);
    sb.append(", privateLocation=").append(privateLocation);
    sb.append('}');
    return sb.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location driver = (Location) o;
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

  public PGgeometry getGeom() {
    return geom;
  }

  public void setGeom(PGgeometry geom) {
    this.geom = geom;
  }

  public Point getPoint() {
    return point;
  }

  public void setPoint(Point point) {
    this.point = point;
  }

  public boolean isPrivateLocation() {
    return privateLocation;
  }

  public void setPrivateLocation(boolean privateLocation) {
    this.privateLocation = privateLocation;
  }
  public String getPointName() {
    return pointName;
  }

  public void setPointName(String pointName) {
    this.pointName = pointName;
  }
}
