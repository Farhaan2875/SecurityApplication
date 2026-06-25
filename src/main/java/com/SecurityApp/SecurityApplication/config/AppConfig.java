package com.SecurityApp.SecurityApplication.config;


import com.SecurityApp.SecurityApplication.auth.AuditorAwareImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


//Bean name becomes:getAuditorAwareImpl
//
//because by default: method name
//    “Use the bean named getAuditorAwareImpl as the auditing provider.”
//
//Bean name = method name
//Why not automatically choose?
//
//Because Spring can have multiple beans of same type.

@Configuration
@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl")
//add this annotation to any config class to enable auditing
public class AppConfig {
    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
    @Bean
    AuditorAware<String> getAuditorAwareImpl(){
        return new AuditorAwareImpl();
    }

    // DIFFERENCE BETWEEN ENCODING AND ENCRYPTION
    //Bcrypt is a one way hash you cannot get your password from encrypted (whereas in other encryption you can get password back from encrypted data)
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
