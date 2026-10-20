package com.e_commerceapp.E_Commerce.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerceapp.E_Commerce.model.App_enum;
import com.e_commerceapp.E_Commerce.model.Role;

public interface roleRepository extends JpaRepository<Role,Long>  {
     public Optional<Role>  findByappenum(App_enum appenum);
}
