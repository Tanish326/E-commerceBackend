package com.e_commerceapp.E_Commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.e_commerceapp.E_Commerce.model.Product;
import java.util.*;
@Repository 
public interface ProductRepo extends JpaRepository<Product,Long> {

    public List<Product> findByproductNameContainingIgnoreCase(String keyword);
   public Page<Product> findByCategoryCategoryId(
        Long categoryId,
        Pageable pageable
);
} 
