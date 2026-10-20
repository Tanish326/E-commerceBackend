package com.e_commerceapp.E_Commerce.Exceptions;

import java.util.HashMap;
import java.util.Map;
import  com.e_commerceapp.E_Commerce.Exceptions.ResourceNotFoundException;
import com.e_commerceapp.E_Commerce.Payload.Apiresponse;
import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionhandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> MyMethodArguementNotValidException(MethodArgumentNotValidException e){
        Map<String,String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String fieldname = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldname, message);
        });
        return ResponseEntity.badRequest().body(response);

    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> MyResourceNotFoundException(ResourceNotFoundException e){
               String message = e.getMessage();
               return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<String>MyApiException(ApiException e){
        String message = e.getMessage();
        return ResponseEntity.badRequest().body(message);
    }
  
  

}
