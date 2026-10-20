package com.e_commerceapp.E_Commerce.model;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.*;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Builder;
@Entity
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
   @NotBlank 
    private String username;
   @NotBlank 
   @Email 
    private String email;

    private String password;
  private boolean enabled;
   @ToString.Exclude
@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(
    name = "users_roles",
    joinColumns = @JoinColumn(name = "users_userid", referencedColumnName = "userid"),
    inverseJoinColumns = @JoinColumn(name = "roles_roleid", referencedColumnName = "roleid")
)
private Set<Role> roles = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "users",cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Set<Address> addresses = new HashSet<>();
     @ToString.Exclude
    @OneToMany
    private Set<Product> products = new HashSet<>();

    @ToString.Exclude
    @OneToOne(mappedBy = "users")
    private Cart cart;

    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public void setroles(Set<Role>st){
        this.roles = st;
    }
}