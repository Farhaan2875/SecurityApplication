package com.SecurityApp.SecurityApplication.auth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Anuj Kumar Sharma");
        //using manually but with spring security can get directly (learn later)
        //get security context
        //get authentication
        //get the principle
        //get the username from principle
    }
}
