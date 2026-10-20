package com.e_commerceapp.E_Commerce.model;

import java.time.LocalDate;
import java.util.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@AllArgsConstructor 
@NoArgsConstructor 
@Data 
public class Order {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;
    
    @Email 
    @Column(nullable = false)
    private String email;
    private LocalDate Orderat;
    private Double totalamount;
    private String OrderStatus;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<OrderItems> orderItems;

    @ManyToOne
    @JoinColumn(name = "addressid")
    private Address address;

    @OneToOne 
    @JoinColumn(name = "paymentid")
    private Payment payment;
}
