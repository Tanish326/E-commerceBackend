package com.e_commerceapp.E_Commerce.Payload;

import java.util.Locale.Category;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class ProductDto {
   private Long ProductId;
   private String productName;
   private String image;
   private Double Price;
   private Double Specialprice;
   private Double Discount;
   private Long quantity;


    
}