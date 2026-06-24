package com.SecurityApp.SecurityApplication.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {  // CONFIGURING THE SECURITY FILTER CHAIN
    // ALL THE HTTP REQUESTS HAVE TO GO THROUGH THIS FILTER CHAIN BEFORE REACHING THE CONTROLLERS
    // BY DEFAULT WE HAVE THE LOGIN PAGE AND SPRING SECURITY DEPENDENCY BY DEFAULT USES SESSION ID AND CSRF TOKENS

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/posts").permitAll() // permits these without authentication for all
                        .requestMatchers("/posts/**").hasAnyRole("ADMIN") //only ADMIN ROLE allowed after posts
                        .anyRequest().authenticated()) // authenticates all https requests
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .formLogin(Customizer.withDefaults()); //WONT BE USING FORMS AND CSRF AND SESSION -> LATEST SECURITY TREND IS JWT TOKENS FOR STATELESS BEHAVIOUR
        return httpSecurity.build();
    }

    //ONLY FRO TESTING -> IN PRODUCTION WE NEED TO STORE IN DATABASE (ROLES AND USER DETAILS)
    @Bean
    UserDetailsService inMemoryUserDetailsService(){
        UserDetails normalUser = User
                .withUsername("farhaan")
                .password(passwordEncoder().encode("farhaan123"))
                .roles("USER") //custom roles
                .build();

        UserDetails adminUser = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN") //custom roles
                .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
