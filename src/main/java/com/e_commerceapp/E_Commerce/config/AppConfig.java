package com.e_commerceapp.E_Commerce.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.*;
@Configuration 
public class AppConfig {
    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }
    
}
