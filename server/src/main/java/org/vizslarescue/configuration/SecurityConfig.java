package org.vizslarescue.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.vizslarescue.service.user.UserController;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  private UserController userService;  

  @Override  
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.userDetailsService(userService)
      .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http.csrf().disable()
      .formLogin()
      .defaultSuccessUrl("/", true)
    .and()
      .logout()
      .logoutSuccessUrl("/")
    .and()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST).hasRole("ADMIN")
      .antMatchers(HttpMethod.PUT).hasRole("ADMIN")
      .antMatchers(HttpMethod.DELETE).hasRole("ADMIN")
      .antMatchers("/api/**").hasAnyRole("USER", "ADMIN");
  }

  @Bean
  public PasswordEncoder passwordEncoder()
  {
    return new BCryptPasswordEncoder();
  }
}
