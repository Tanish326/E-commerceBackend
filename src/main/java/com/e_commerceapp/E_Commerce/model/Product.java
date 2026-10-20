package com.e_commerceapp.E_Commerce.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@AllArgsConstructor 
@NoArgsConstructor 
@Data 
public class Product {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProductId;
    @NotBlank 
    @Size(min = 2)
    private String productName;
    
    private Double Price;
    private String image;
    private Double Discount;
    private Long quantity;
    private LocalDateTime CreatedAt;
    private Double Specialprice;

    
    
@ManyToOne
@JoinColumn(name = "category_id")
private Category category;
@ManyToOne
@JoinColumn(name="userid")
private Users user;

@OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)

private List<CartItems> cartItems;
    
}

