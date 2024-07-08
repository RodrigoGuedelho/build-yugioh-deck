package com.guedelho.buildYourDeck.config;

import com.guedelho.buildYourDeck.exceptions.BadRequestException;
import com.guedelho.buildYourDeck.responseDtos.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus()
    public ResponseEntity<Object> badRequestException(BadRequestException ex) {
        ResponseException response = new ResponseException(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response) ;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> constraintViolationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        String errorMessage = "Erro de validações: ";
        for (FieldError error : result.getFieldErrors())
            errorMessage += error.getDefaultMessage() + ", ";
        errorMessage = errorMessage.substring(0, errorMessage.length()-3);
        ResponseException response = new ResponseException(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response) ;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus()
    public ResponseEntity<Object> badCredentialsException(BadCredentialsException ex) {
        ResponseException response = new ResponseException(HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(response) ;
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseStatus()
    public ResponseEntity<Object> internalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
        ResponseException response = new ResponseException(HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(), "Usuário não existe.");
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(response) ;
    }

    /*@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        ResponseException response = new ResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response) ;
    }*/


}
