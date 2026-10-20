package com.e_commerceapp.E_Commerce.Payload;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 

@AllArgsConstructor 
@NoArgsConstructor 
public class Loginrequest {
    @NotBlank 
    private String username;
    @NotBlank 
    private String password;
    
}
