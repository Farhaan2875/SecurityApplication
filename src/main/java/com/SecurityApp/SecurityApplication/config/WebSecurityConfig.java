package com.SecurityApp.SecurityApplication.config;


import com.SecurityApp.SecurityApplication.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {  // CONFIGURING THE SECURITY FILTER CHAIN
    // ALL THE HTTP REQUESTS HAVE TO GO THROUGH THIS FILTER CHAIN BEFORE REACHING THE CONTROLLERS
    // BY DEFAULT WE HAVE THE LOGIN PAGE AND SPRING SECURITY DEPENDENCY BY DEFAULT USES SESSION ID AND CSRF TOKENS

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/posts","/error","/auth/**").permitAll() // permits these without authentication for all
//                        .requestMatchers("/posts/**").authenticated() //only ADMIN ROLE allowed after posts (.hasanyrolemethod)
                        .anyRequest().authenticated()) // authenticates all https requests
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        //                .formLogin(Customizer.withDefaults());
// WONT BE USING FORMS AND CSRF AND SESSION -> LATEST SECURITY TREND IS JWT TOKENS FOR STATELESS BEHAVIOUR
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }



    //ONLY FRO TESTING -> IN PRODUCTION WE NEED TO STORE IN DATABASE (ROLES AND USER DETAILS)
//    @Bean
//    UserDetailsService inMemoryUserDetailsService(){
//        UserDetails normalUser = User
//                .withUsername("farhaan")
//                .password(passwordEncoder().encode("farhaan123"))
//                .roles("USER") //custom roles
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN") //custom roles
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }



}
