package com.e_commerceapp.E_Commerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class OrderItems {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderitemid;
    private Double Discount;
    private Long quantity;
    private Double Productprice;
    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "orderid")
    private Order order;
}
