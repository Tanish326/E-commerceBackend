package com.e_commerceapp.E_Commerce.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Signuprequest {
    @NotBlank 
    @Email 
    
    private String email;
    @NotBlank 
    @Size(min = 5 , max = 10)
    private String username;
    @NotBlank 

    private String password;
    private Set<String>Role;
    
}
