package com.demo.springboottest.security;

import com.demo.springboottest.io.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurity {
    @Autowired
    UserRepository userRepository;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{

        //configure
        AuthenticationManagerBuilder authenticationManagerBuilder=httpSecurity.getSharedObject(
                AuthenticationManagerBuilder.class
        );
        //authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager=authenticationManagerBuilder.build();
        httpSecurity
                .cors().disable()
                .csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/users/login")
                .permitAll()
                .anyRequest().authenticated().and()
                .addFilter(getAuthenticationFilter(authenticationManager))
                .addFilter(new AuthorizationFilter(authenticationManager,userRepository))
                .authenticationManager(authenticationManager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.headers().frameOptions().disable();
        return httpSecurity.build();

    }


    protected AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception{
        AuthenticationFilter filter=new com.demo.springboottest.security.AuthenticationFilter(
                authenticationManager
        );
        filter.setFilterProcessesUrl("/users/login");
        return  filter;
    }
}
