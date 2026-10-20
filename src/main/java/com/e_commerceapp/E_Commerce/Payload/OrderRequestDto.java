package com.e_commerceapp.E_Commerce.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class OrderRequestDto {
    private Long addressid;
    private String PaymentMethod;
    private String pgname;
   private Long pgPayementId;
     private String pgstatus;
    private String pgResponsemessage;
    
}
