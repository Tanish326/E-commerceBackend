package com.e_commerceapp.E_Commerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.OnError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
public class Payment {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payementid;

    @OneToOne(mappedBy = "payment")
    private Order order;

    @NotBlank 
    private String PaymentMethod;

    private Long pgpaymentid;
    private String pgpaymentname;
    private String pgstatus;
    private String pgResponsemessage;

    public Payment(String paymentmethod,Long pgid,String pgname, String pgstatus, String pgresponse){
        this.PaymentMethod = paymentmethod;
        this.pgstatus = pgstatus;
        this.pgpaymentid = pgid;
        this.pgResponsemessage = pgresponse;
        this.pgpaymentname = pgname;
    }
    

    
}
