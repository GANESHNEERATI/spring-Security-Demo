package com.ganesh.springsecurity.config;

import com.ganesh.springsecurity.helper.JWTTokenHelper;
import com.ganesh.springsecurity.service.CustomUserService;
import io.jsonwebtoken.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserService customUserService;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
  //http.authorizeRequests((request)->request.antMatchers("/h2-console/**").permitAll().anyRequest().authenticated()).httpBasic();
     http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
             .authenticationEntryPoint(restAuthenticationEntryPoint).and()
             .authorizeRequests((request)->request.antMatchers("/h2-console/**","/api/v1/auth/login").permitAll()
                     .antMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest().authenticated())
             .addFilterBefore(new JWTAuthenticationFilter( customUserService, jwtTokenHelper),
                     UsernamePasswordAuthenticationFilter.class);

    // http.formLogin();
    // http.cors();

  //h2 console
     http.csrf().disable().headers().frameOptions().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
     /*inmemory authentication */
    // auth.inMemoryAuthentication().withUser("ganesh").password(passwordEncoder().encode("ganesh")).authorities("USER","ADMIN");
     /* database authentication */
     auth.userDetailsService(customUserService).passwordEncoder(passwordEncoder());

 }


    @Bean
    public PasswordEncoder passwordEncoder(){
     return new  BCryptPasswordEncoder();
    }


}
