package com.e_commerceapp.E_Commerce.jwt.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.e_commerceapp.E_Commerce.model.*;
import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Repository.UserRepository;
import jakarta.transaction.Transactional;
@Service 
public class UserDetailsServiceImpl implements  UserDetailsService{
    @Autowired 
    private UserRepository UserRepository;
    @Override 
    @Transactional 
    public UserDetails loadUserByUsername(String username){
     Optional<Users> user = UserRepository.findByusername(username);
     if(user.isEmpty()){
        throw new ApiException("user not found");
     }
     return CustomUserDetails.build(user.get());
          
    }
}
