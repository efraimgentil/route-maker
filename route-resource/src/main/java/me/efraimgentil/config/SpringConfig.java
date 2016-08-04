package me.efraimgentil.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 10/07/16.
 */

@Configuration
@ComponentScan(basePackages = {  "me.efraimgentil.validator" , "me.efraimgentil.service"  })
public class SpringConfig {

  public final static String GOOGLE_KEY = "my_google_api_key";

  @Bean
  @Qualifier(SpringConfig.GOOGLE_KEY)
  public String getGoogleApiKey(){
    String google_direction_key = System.getenv("GOOGLE_DIRECTION_KEY");
    if(google_direction_key == null) {
      throw new RuntimeException("You didn't set the google direction key as the environment variable name GOOGLE_DIRECTION_KEY");
    }
    return google_direction_key;
  }


  @Bean
  public MessageSource messageSource(){
    ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasename("messages");
    return ms;
  }
}
