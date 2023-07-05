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
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/addRestaurant").permitAll();
                    auth.requestMatchers("/yourProfile").permitAll();
                    auth.requestMatchers("/changePassword").permitAll();
                    auth.requestMatchers("/editRestaurant/**").permitAll();
                    auth.requestMatchers("/editDish/**").permitAll();
                    auth.requestMatchers("/addToCart/**").permitAll();
                    auth.requestMatchers("/orderCart/**").permitAll();
                    auth.requestMatchers("/cartEntry/remove/*").permitAll();
                    auth.requestMatchers("/forgotPassword/**").permitAll();
                    auth.requestMatchers("/changeForgottenPassword/**").permitAll();
                    auth.requestMatchers("/restaurant/review/*").permitAll();
                    auth.requestMatchers("/restaurant/review/**").permitAll();


                    auth.requestMatchers("/registration").permitAll();
                    auth.requestMatchers("/css/**").permitAll();
                    auth.requestMatchers("/img/**").permitAll();
                    auth.requestMatchers("/restaurant/*").permitAll();
                    auth.requestMatchers("/dish/*").permitAll();

                    auth.requestMatchers("/js/**").permitAll();
                    auth.requestMatchers("/api/users").permitAll();

                    auth.requestMatchers("/restaurant/*/addDish").permitAll();
                    auth.requestMatchers("/dish/*/update").hasRole("SELLER");
                    auth.requestMatchers("/dish/*/modify").hasRole("SELLER");
                    auth.requestMatchers("/dish/*/remove").hasRole("SELLER");
                    auth.requestMatchers("/removeRestaurant/*").hasRole("SELLER");
                    auth.requestMatchers("/food-order/*/change-status").hasRole("SELLER");
                    auth.requestMatchers("/orderDashboard").hasRole("SELLER");
                    auth.requestMatchers("/food-order/*/*").hasRole("SELLER");

                    auth.requestMatchers("/addToCard/**").permitAll();
                    auth.requestMatchers("/sendFoodOrder").hasRole("BUYER");
                    auth.requestMatchers("/orderHistory/foodOrder/*").hasRole("BUYER");




                    auth.requestMatchers("/addDish/*").permitAll();
                    auth.requestMatchers("/orderHistory").permitAll();
                    auth.requestMatchers("/sellerPage").permitAll();
                    auth.requestMatchers("/orderCart").permitAll();
                    auth.requestMatchers("/navBar").permitAll();
                    auth.requestMatchers("/forgotPassword").permitAll();
                    auth.requestMatchers("/forgotPassword/*/*").permitAll();
                    auth.requestMatchers("/ratingPage").permitAll();


                })
                .httpBasic();


        httpSecurity.formLogin()
                .loginPage("/login").defaultSuccessUrl("/home").permitAll()
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/home").permitAll()
                .and()
                .csrf().disable().authorizeHttpRequests()
                .and()
                .cors().disable().authorizeHttpRequests();

        return httpSecurity.build();
    }

}
