package me.efraimgentil.model;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 09/07/16.
 */
public class Driver {

  private Integer id;
  private String name;

  public Driver() {
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Driver{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
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
