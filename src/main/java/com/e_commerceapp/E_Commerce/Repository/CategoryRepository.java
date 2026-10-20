package com.e_commerceapp.E_Commerce.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.e_commerceapp.E_Commerce.model.Category;

@Repository 
public interface CategoryRepository extends JpaRepository<Category,Long> {
  Category findCategoryByName(String name);
    
}
