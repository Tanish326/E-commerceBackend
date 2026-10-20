package com.e_commerceapp.E_Commerce.Payload;
import com.e_commerceapp.E_Commerce.model.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class OrderDto {
    @Id 
    private Long orderid;
    private List<OrderItemDto> orderitemdto;
    private PaymentDto paymentDto;
    private String email;
    private LocalDate Orderat;
    private Double totalamount;
    private String OrderStatus;
    private Long addressid;
    
}
