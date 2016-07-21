package me.efraimgentil.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.postgis.PGgeometry;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 15/07/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {

  private Double lat;
  private Double lng;

  public Point() {  }

  public Point(Double lat, Double lng) {
    this.lat = lat;
    this.lng = lng;
  }

  public Point(PGgeometry geom){
    org.postgis.Point p = (org.postgis.Point) geom.getGeometry();
    this.lat = p.getX();
    this.lng = p.getY();
  }

  public String toGeom(){
    return String.format("SRID=3857;POINT(%s %s)" ,lat , lng);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Point{");
    sb.append("lat=").append(lat);
    sb.append(", lng=").append(lng);
    sb.append('}');
    return sb.toString();
  }

  public Double getLat() {
    return lat;
  }

  public void setLat(Double lat) {
    this.lat = lat;
  }

  public Double getLng() {
    return lng;
  }

  public void setLng(Double lng) {
    this.lng = lng;
  }
}
