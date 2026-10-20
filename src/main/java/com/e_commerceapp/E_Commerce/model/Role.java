package com.e_commerceapp.E_Commerce.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity 
@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Role {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private Long roleid;
    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20,name = "role_name")
    private App_enum appenum;

  
    
}
