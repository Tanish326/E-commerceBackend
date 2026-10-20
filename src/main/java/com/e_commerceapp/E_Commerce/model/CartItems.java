package com.e_commerceapp.E_Commerce.model;

import java.util.HashSet;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class CartItems {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CarditemId;
    private Double Discount;
    private Long quantities;
    @ManyToOne
    @JoinColumn(name = "cartid")
    private Cart cart;

    
    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;
    

   
    
}
