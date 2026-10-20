package com.e_commerceapp.E_Commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_commerceapp.E_Commerce.model.OrderItems;

@Repository 
public interface OrderItemrepo extends JpaRepository<OrderItems,Long> {
    
}
