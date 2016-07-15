package me.efraimgentil.model;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 15/07/16.
 */
public class Point {

  private double lon;
  private double lat;

  public Point() {  }

  public Point(double lat, double lon) {
    this.lat = lat;
    this.lon = lon;
  }

  public String toGeom(){
    return String.format("SRID=3857;POINT(%s %s)" ,lat , lon );
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Point{");
    sb.append("lat=").append(lat);
    sb.append(", lon=").append(lon);
    sb.append('}');
    return sb.toString();
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }
}
