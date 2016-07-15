package me.efraimgentil.service;

import me.efraimgentil.config.DatabaseConfig;
import me.efraimgentil.config.SpringConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgis.PGgeometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 14/07/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class , DatabaseConfig.class })
public class LocationServiceIT {


  @Autowired
  DataSource dataSource;
  @Test
  public void does() throws SQLException {
    try(Connection conn = dataSource.getConnection()){
      ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM public.location");
      while(rs.next()){

        System.out.println( rs.getObject("point") );
        PGgeometry geom = (PGgeometry)rs.getObject("point");
        System.out.println(geom.toString());
        System.out.println(geom.getValue());
        System.out.println(geom.getType());
        System.out.println(geom.getGeoType());
        System.out.println(geom.getGeometry());


      }
    }
  }

}
