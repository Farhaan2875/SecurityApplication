package com.SecurityApp.SecurityApplication.services;

import com.SecurityApp.SecurityApplication.entities.User;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    // ONLY NEED TWO METHODS -> ONE TO GENERATE JWT TOKEN AND OTHER TO VERIFY


    @Value("${jwt.secretKey}")
    private String jwtSecretKey;


    public String generateToken(User user){
        Jwts.builder()
                .subject(user.getId().toString()) //subject is usd to identify user

    }
}
