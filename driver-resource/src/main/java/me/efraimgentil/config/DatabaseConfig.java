package me.efraimgentil.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/06/16.
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true )
public class DatabaseConfig {

  @Bean(name = "database")
  @Qualifier(value = "database")
  public ResourceBundle dataBaseProperties(){
    return ResourceBundle.getBundle("database");
  }

  @Bean
  public DataSource dataSource( @Qualifier("database") ResourceBundle env ) {
    HikariDataSource ds = new HikariDataSource();
    ds.setDriverClassName(env.getString("pg.driver"));
    ds.setJdbcUrl(env.getString("pg.url"));
    ds.setUsername(env.getString("pg.username"));
    ds.setPassword( env.getString("pg.password") );
    ds.setMaximumPoolSize( 5 );
    ds.setMinimumIdle( 0 );
    ds.setConnectionTestQuery("SELECT 1");
    return ds;
  }

  @Bean(name = "transactionManager")
  public DataSourceTransactionManager transactionManager( DataSource ds){
    return new DataSourceTransactionManager( ds );
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource ds){
    return new JdbcTemplate( ds );
  }


}
