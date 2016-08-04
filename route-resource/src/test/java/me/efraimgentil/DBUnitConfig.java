package me.efraimgentil;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 03/08/16.
 */

@Configuration
public class DBUnitConfig {


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
