package me.efraimgentil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/02/16.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true , securedEnabled = false, proxyTargetClass = true)
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {



  @Autowired
  TokenStore tokenStore;

  private TokenExtractor tokenExtractor = new BearerTokenExtractor();

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    /*http.addFilterBefore( new CORSFilter() , AbstractPreAuthenticatedProcessingFilter.class  )
            .addFilterAfter(new OncePerRequestFilter() {
              @Override
              protected void doFilterInternal(HttpServletRequest request,
                                              HttpServletResponse response, FilterChain filterChain)
                      throws ServletException, IOException {
                // We don't want to allow access to a resource with no token so clear
                // the security context in case it is actually an OAuth2Authentication
                *//*if (tokenExtractor.extract(request) == null) {
                  SecurityContextHolder.clearContext();
                }*//*
                filterChain.doFilter(request, response);
              }
            }, AbstractPreAuthenticatedProcessingFilter.class);*/
    http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and().authorizeRequests()
            .anyRequest().permitAll();
  }

  /*@Bean
  public AuthenticationManager authenticationManager(){
    DefaultTokenServices defaultTokenServices = tokenServices();
    OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
    oAuth2AuthenticationManager.setTokenServices( defaultTokenServices );
    return oAuth2AuthenticationManager;
  }*/

  @Override
  public void configure(final ResourceServerSecurityConfigurer config) {
    config.tokenServices(tokenServices());
    config.resourceId("driver-resource");
  }

  @Bean
  public TokenStore tokenStore() {
    JwtTokenStore jwtTokenStore = new JwtTokenStore( accessTokenConverter() );
    return jwtTokenStore;
  }
  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("123456");
    converter.setAccessTokenConverter( new MyAccessTokenConverter() );
    return converter;
  }
  @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore());
    return defaultTokenServices;
  }


 /* @Bean
  @Primary
  public DefaultTokenServices tokenServices() {
    final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore);
    return defaultTokenServices;
  }

  @Bean
  public TokenStore tokenStore( DataSource ds){
    return new JdbcTokenStore( ds );
  }
*/
}
