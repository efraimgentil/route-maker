package me.efraimgentil.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/06/16.
 */
@Configuration
public class DatabaseConfig {


  @Bean(name = "database")
  @Qualifier(value = "database")
  public ResourceBundle dataBaseProperties(){
    return ResourceBundle.getBundle("database");
  }


  @Bean
  public DataSource dataSource( @Qualifier("database") ResourceBundle env ) {
    HikariDataSource ds = new HikariDataSource();
    ds.setDriverClassName("org.postgresql.Driver");
    ds.setJdbcUrl(  env.getString("pg.url"));
    ds.setUsername( env.getString("pg.username"));
    ds.setPassword( env.getString("pg.password") );
    ds.setMaximumPoolSize( 5 );
    ds.setMinimumIdle( 0 );
    ds.setIdleTimeout( 1000 * 60 * 5   ); // 5 minutes
    ds.setMaxLifetime( 1000 * 60 * 15  ); // 15 minutes
    ds.setValidationTimeout( 1000 * 10 ); // 10 seconds
    ds.setConnectionTestQuery(env.getString("pg.testQuery"));
    return ds;
  }

}
