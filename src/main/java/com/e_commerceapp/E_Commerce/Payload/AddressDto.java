package com.e_commerceapp.E_Commerce.Payload;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class AddressDto {
    @Id 
    private Long AddressId;
    private String Street;
    private String buildingname;
    private String country;
    private String City;
    private String State;
    private Long Pincode;

    
}
