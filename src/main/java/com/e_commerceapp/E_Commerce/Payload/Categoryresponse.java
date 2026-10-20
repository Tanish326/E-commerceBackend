package com.e_commerceapp.E_Commerce.Payload;
import  com.e_commerceapp.E_Commerce.Payload.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data 
@AllArgsConstructor 
@NoArgsConstructor
public class Categoryresponse {
    private List<CategoryDto>Content;
    private Integer Pagesize;
    private Integer pagenumber;
    private  Integer totalpages;
    private  Long totalelements;
    private  Boolean Lastpage;
}
