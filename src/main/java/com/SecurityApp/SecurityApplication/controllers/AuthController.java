package com.SecurityApp.SecurityApplication.controllers;


import com.SecurityApp.SecurityApplication.dto.LoginDTO;
import com.SecurityApp.SecurityApplication.dto.SignUpDTO;
import com.SecurityApp.SecurityApplication.dto.UserDTO;
import com.SecurityApp.SecurityApplication.services.AuthService;
import com.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO = userService.signUp(signUpDTO);
        return ResponseEntity.ok(userDTO);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        String token = authService.login(loginDTO);

        Cookie cookie = new Cookie("token", token); //to store jwt token in cookies of browser
        cookie.setHttpOnly(true); // always make true so that it in only stored in browser cookies and no attacks on website can steal it
        //can only be accesses by http methods and not by JavaScript
        response.addCookie(cookie);


        return ResponseEntity.ok(token);
    }

}
