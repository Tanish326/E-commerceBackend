package com.e_commerceapp.E_Commerce.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class Hello {
    @GetMapping("/hello")
    public String hello(){
        return "Hello, World!";
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/ye")
    public String yeeah(){
        return "ye";
    }

    
}
