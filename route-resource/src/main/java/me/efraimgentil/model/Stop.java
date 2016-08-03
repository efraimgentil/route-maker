package me.efraimgentil.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.postgis.PGgeometry;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 25/07/16.
 */
public class Stop {

  private Long id;
  private Long routeId;
  private Integer order;
  private String passenger;

  @JsonIgnore
  private PGgeometry geom;

  private Point point;

  public Stop() { }

  public Stop(String passenger , Point point ) {
    this.passenger = passenger;
    this.point = point;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Stop{");
    sb.append(", id=").append(id);
    sb.append(", routeId=").append(routeId);
    sb.append(", order=").append(order);
    sb.append(", passenger='").append(passenger).append('\'');
    sb.append(", point=").append(point);
    sb.append('}');
    return sb.toString();
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getPassenger() {
    return passenger;
  }
  public void setPassenger(String passenger) {
    this.passenger = passenger;
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

  public Long getRouteId() {
    return routeId;
  }

  public void setRouteId(Long routeId) {
    this.routeId = routeId;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }
}
