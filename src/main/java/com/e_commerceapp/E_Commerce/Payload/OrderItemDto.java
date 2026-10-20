package com.e_commerceapp.E_Commerce.Payload;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.e_commerceapp.E_Commerce.model.*;;
@NoArgsConstructor 
@Data 
@AllArgsConstructor 
public class OrderItemDto {

    @Id 
    private Long orderItemid;
    private ProductDto productDto;
    private Long quantity;
    private Double Discount;
    private Double productprice;
    
}
