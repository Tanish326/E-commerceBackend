package com.e_commerceapp.E_Commerce.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.e_commerceapp.E_Commerce.model.*;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.users.email = :email")
    Cart findByemail(@Param ("email") String email);
    
    Cart findByusers(Users users);
   @Query("""
    SELECT c
    FROM Cart c
    JOIN FETCH c.cartItems ci
    WHERE ci.product.ProductId = :ProductId
""")
Optional<Cart> findCartByProductId(@Param("ProductId") Long productId);
}
