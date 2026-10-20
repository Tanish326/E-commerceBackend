package com.e_commerceapp.E_Commerce.Exceptions;

public class ApiException extends  RuntimeException {
    public static final Long Uid = 1L;
    public  ApiException(String message){
        super(message);

    }

    
}
