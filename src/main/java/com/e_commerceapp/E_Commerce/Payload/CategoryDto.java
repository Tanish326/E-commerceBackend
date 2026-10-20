package com.e_commerceapp.E_Commerce.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class CategoryDto {
    private String name;
    private String description;
   private Long id;
}
