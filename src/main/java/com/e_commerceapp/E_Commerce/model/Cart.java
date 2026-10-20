package com.e_commerceapp.E_Commerce.model;

import java.util.ArrayList;
import java.util.List;
import com.e_commerceapp.E_Commerce.model.CartItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 

@NoArgsConstructor 
@Data 
public class Cart {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CartId;
     private Double TotalPrice;
    @OneToOne 
    @JoinColumn(name = "userid")
    private Users users;
    public  Cart(Long cartid,Double price,Users users){
        this.CartId = cartid;
        this.TotalPrice = price;
        this.users =users;
    }

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    private List<CartItems> cartItems = new ArrayList<>();
    
}
