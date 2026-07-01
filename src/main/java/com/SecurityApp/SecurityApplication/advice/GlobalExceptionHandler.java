package com.SecurityApp.SecurityApplication.advice;

import com.SecurityApp.SecurityApplication.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.core.AuthenticationException;

@RestControllerAdvice //automatic
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class) //WILL AUTOMATICALLY RUN AND HANDLE EXCEPTION OF THIS TYPE
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex){
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED,ex.getMessage());
        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }

    // it gives 500 error not jwt error because the custom filter chain takes it into another context called
    // the filter context and globalexception handler works if the context is with dispatcher servlet
    // we use HandlerExceptionResolver to tranfer the contexts - from filter to servlet/dispatcher context
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex){
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED,ex.getMessage());
        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }
}
