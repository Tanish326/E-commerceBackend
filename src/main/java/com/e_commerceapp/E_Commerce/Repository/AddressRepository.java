package com.e_commerceapp.E_Commerce.Repository;

import org.springframework.stereotype.Repository;

import com.e_commerceapp.E_Commerce.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository 
public interface AddressRepository extends JpaRepository<Address,Long>{
    
}
