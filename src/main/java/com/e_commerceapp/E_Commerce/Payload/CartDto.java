package com.e_commerceapp.E_Commerce.Payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class CartDto  {
    private Long CartId;
    private Double TotalPrice;
    private List<ProductDto> productDtos;
}
