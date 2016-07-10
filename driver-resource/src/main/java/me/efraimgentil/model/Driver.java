package me.efraimgentil.model;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 09/07/16.
 */
public class Driver {

  private Integer id;
  private String name;

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

}
