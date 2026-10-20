package com.e_commerceapp.E_Commerce.model;

import java.util.ArrayList;
import java.util.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data 

@NoArgsConstructor 
public class Address {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressid;
    @NotBlank 
    @Size(min = 5,message = "street must be of 5 ch min")
    private String Street;
     @NotBlank 
    private String buildingname;
     @NotBlank 
    private String City;
     @NotBlank 
    private String State;
     @NotBlank 
    private String country;
     
    private Long Pincode;

    public Address(Long id,String street,String buildingname,String city,String state,String country,Long pincode){
        this.addressid = id;
        this.Street = street;
        this.buildingname = buildingname;
        this.City = city;
        this.State = state;
        this.country = country;
        this.Pincode = pincode;
    }


@ManyToOne
@JoinColumn(name = "userid")
private Users users;
    
}


