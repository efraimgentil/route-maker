package me.efraimgentil;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import com.zaxxer.hikari.HikariDataSource;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ResourceBundle;


/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 03/08/16.
 */

@Configuration
@EnableTransactionManagement(proxyTargetClass = true )
public class DBUnitConfig {

    @Bean(name = "database")
    @Qualifier(value = "database")
    public ResourceBundle dataBaseProperties(){
      return ResourceBundle.getBundle("database");
    }


    @Bean(name="postgresDatabase")
    public DataSource dataSource( @Qualifier("database") ResourceBundle env ) {
      HikariDataSource ds = new HikariDataSource();
      ds.setDriverClassName("org.postgresql.Driver");
      ds.setJdbcUrl(env.getString("pg.test.url"));
      ds.setUsername(env.getString("pg.username"));
      ds.setPassword(env.getString("pg.password"));
      ds.setMaximumPoolSize(5);
      ds.setMinimumIdle(0);
      ds.setIdleTimeout(1000 * 60 * 5); // 5 minutes
      ds.setMaxLifetime(1000 * 60 * 15); // 15 minutes
      ds.setValidationTimeout( 1000 * 10 ); // 10 seconds
      ds.setConnectionTestQuery(env.getString("pg.testQuery"));
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


  @Bean
  public DefaultDataTypeFactory postgresDataTypeFactory(){
    return new PostgresqlDataTypeFactory();
  }

  @Bean
  public DatabaseConfigBean dbUnitDatabaseConfig(){
    DatabaseConfigBean cofigBean = new  DatabaseConfigBean();
    cofigBean.setDatatypeFactory(postgresDataTypeFactory());
    return cofigBean;
  }

  @Bean
  public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(DataSource dataSource) {
    DatabaseDataSourceConnectionFactoryBean dbConnection;
    dbConnection = new DatabaseDataSourceConnectionFactoryBean( dataSource );
    dbConnection.setDatabaseConfig(dbUnitDatabaseConfig());
    return dbConnection;
  }

  /*<bean id="sqlDataTypeFactory" class ="org.dbunit.ext.mysql.MySqlDataTypeFactory" />

  <bean id="dbUnitDatabaseConfig" class="com.github.springtestdbunit.bean.DatabaseConfigBean">
  <property name = "datatypeFactory" ref = "sqlDataTypeFactory" />
  </bean>
  <bean id="dbUnitDatabaseConnection" class="com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean">
  <property name="databaseConfig" ref="dbUnitDatabaseConfig"/>
  <property name="dataSource" ref="dataSource" />
  </bean>*/

}
