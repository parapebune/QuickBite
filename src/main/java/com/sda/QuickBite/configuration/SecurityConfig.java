package com.sda.QuickBite.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity



public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/home").permitAll();
                    auth.requestMatchers("/error").permitAll();

                    auth.requestMatchers("/registration").permitAll();
                    auth.requestMatchers("/css/*").permitAll();
                    auth.requestMatchers("/img/*").permitAll();
                    auth.requestMatchers("/restaurantPage/*").permitAll();
                    auth.requestMatchers("/dish/*").permitAll();
                    auth.requestMatchers("/js/*").permitAll();
                    auth.requestMatchers("/api/users").hasAnyRole("BUYER", "SELLER");

                    auth.requestMatchers("/addRestaurant").hasRole("SELLER");


                })
                .httpBasic();

        httpSecurity.formLogin()
                .loginPage("/login").defaultSuccessUrl("/home").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable().authorizeHttpRequests()
                .and()
                .cors().disable().authorizeHttpRequests();

        return httpSecurity.build();
    }

}
