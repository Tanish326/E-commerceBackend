package com.e_commerceapp.E_Commerce.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import java.util.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.e_commerceapp.E_Commerce.model.*;
import com.e_commerceapp.E_Commerce.Repository.CategoryRepository;
import com.e_commerceapp.E_Commerce.Repository.ProductRepo;
import com.e_commerceapp.E_Commerce.Repository.UserRepository;
import com.e_commerceapp.E_Commerce.Repository.roleRepository;
import com.e_commerceapp.E_Commerce.jwt.Authentryjwt;
import com.e_commerceapp.E_Commerce.jwt.Authtokenfilter;
import com.e_commerceapp.E_Commerce.jwt.services.CustomUserDetails;
import com.e_commerceapp.E_Commerce.jwt.services.UserDetailsServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Securityconfig {
    @Autowired 
    private UserRepository userRepository;
    @Autowired 
    private roleRepository roleRepository;

    @Autowired
    private Authentryjwt authentryjwt;

    @Autowired
    private Authtokenfilter authtokenfilter;
    @Autowired 
    private ProductRepo productRepo;

    @Autowired 
    private CategoryRepository categoryRepository;
    @Autowired 
    private UserDetailsServiceImpl userDetailsServiceImpl;
     @Bean 
     public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
     }
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http) throws Exception {

        http.csrf(c -> c.disable());

        http.authorizeHttpRequests(requests ->
            requests
                .requestMatchers(
                    "/h2-console/**",
                    "/api/signin",
                    "/api/signup"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
        );

        http.sessionManagement(session ->
            session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
            )
        );

        http.exceptionHandling(exception ->
            exception.authenticationEntryPoint(authentryjwt)
        );

        http.addFilterBefore(
            authtokenfilter,
            UsernamePasswordAuthenticationFilter.class
        );

        http.headers(headers ->
            headers.frameOptions(frameOptions ->
                frameOptions.sameOrigin()
            )
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
  

    };


 
