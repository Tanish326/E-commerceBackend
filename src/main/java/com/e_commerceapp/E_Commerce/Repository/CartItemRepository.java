package com.e_commerceapp.E_Commerce.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.e_commerceapp.E_Commerce.model.CartItems;
@Repository 
public interface CartItemRepository extends JpaRepository<CartItems,Long> {


    @Query("""
    SELECT ci
    FROM CartItems ci
    WHERE ci.product.ProductId = :productid
      AND ci.cart.CartId = :cartid
""")
public  Optional<CartItems>  finditemByCartIdandProductId(@Param("cartid") Long cartid,@Param("productid") Long productid);

@Modifying
@Query("""
    DELETE FROM CartItems ci
    WHERE ci.product.ProductId = :productid
    AND ci.cart.CartId = :cartid
""")
void deleteByCartIdandProductId(
        @Param("productid") Long productid,
        @Param("cartid") Long cartid
);
}
