package me.efraimgentil.config;

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
@ComponentScan(basePackages = {  "me.efraimgentil.validator"  })
public class SpringConfig {


  @Bean
  public MessageSource messageSource(){
    ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasename("messages");
    return ms;
  }
}
