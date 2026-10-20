package com.e_commerceapp.E_Commerce.Payload;
import java.util.*;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Loginresponse {
    private String jwttoken;
    private String username;
    private List<String>roles;

    
}
