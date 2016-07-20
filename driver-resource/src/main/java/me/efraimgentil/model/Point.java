package me.efraimgentil.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.postgis.PGgeometry;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 15/07/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {

  private double lat;
  private double lng;

  public Point() {  }

  public Point(double lat, double lng) {
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

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLng() {
    return lng;
  }

  public void setLng(double lng) {
    this.lng = lng;
  }
}
