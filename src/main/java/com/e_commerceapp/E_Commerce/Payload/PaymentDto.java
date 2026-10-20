package com.e_commerceapp.E_Commerce.Payload;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity 
@AllArgsConstructor 
@NoArgsConstructor 
public class PaymentDto {
    @Id 
    private Long PaymentId;
    private String PaymentMethod;
    private String paymentstatus;
    private String pgpaymentid;
    private String pgstatus;
    private String pgpaymentname;
    
}
