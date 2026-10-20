package com.e_commerceapp.E_Commerce.Payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor 
@NoArgsConstructor 
@Data 
public class ProductResponse {
    private List<ProductDto> productDtos;
    private Integer PageSize;
    private Integer Pages;
    private Integer pageno;
    private Boolean IsLast;
    private Integer totalitems;
    
    
}
