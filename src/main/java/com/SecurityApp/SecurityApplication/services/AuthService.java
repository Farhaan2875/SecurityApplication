package com.SecurityApp.SecurityApplication.services;


import com.SecurityApp.SecurityApplication.dto.LoginDTO;
import com.SecurityApp.SecurityApplication.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    //CREATED ANOTHER SERVICE FOR LOGIN AS AUTHENTICATION MANAGER USES USER SERVICE AND USER SERVICE CALLS
    //AUTHENTICATION MANAGER SO IT CREATES A CIRCULAR DEPENDENCY IN CODE
    //CIRCULAR DEPENDENCY SHOULD NOT EXIST IN APPLICATION

    private final AuthenticationManager authenticationManager; //AUTH MANAGER IS AN INTERFACE WE NEED TO PROVIDE AUTH OBJECT (MANY IMPLEMENTATIONS ARE THERE)
    private final JwtService jwtService;

    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        User user = (User) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
