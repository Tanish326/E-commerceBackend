package com.e_commerceapp.E_Commerce.Payload;
import  com.e_commerceapp.E_Commerce.Payload.Categoryresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Apiresponse {
    private Boolean status;
    private  String Messgae;
    private  Categoryresponse Data;
    
}
