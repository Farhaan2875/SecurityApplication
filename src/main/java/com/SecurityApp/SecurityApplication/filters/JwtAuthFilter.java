package com.SecurityApp.SecurityApplication.filters;


import com.SecurityApp.SecurityApplication.entities.User;
import com.SecurityApp.SecurityApplication.services.JwtService;
import com.SecurityApp.SecurityApplication.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    //WE ARE CREATING A CUSTOM FILTER IN THE FILTER CHAIN TO PROCESS EACH SUBSEQUENT REQUEST AFTER LOGIN
    //WE WILL PUT THIS FILTER IN THE DEFAULT FILTER STHORUGH WEBSECURITYCONFIG CLASS

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        //in the https header which are only key value pair jwt token has "Authorization" as key
        if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")){
            //by convention jwt sent by http from client is "Bearer sdjshvebhv" form
            filterChain.doFilter(request,response);
            return;
        }else{
            String token = requestTokenHeader.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromToken(token);
            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User user = userService.getUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user,null,null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // will put additional details like device info and ip for us to use and also to prevent against ddos attacks

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request,response);
            }

        }

    }
}
