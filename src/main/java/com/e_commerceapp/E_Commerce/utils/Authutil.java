package com.e_commerceapp.E_Commerce.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Exceptions.ResourceNotFoundException;
import com.e_commerceapp.E_Commerce.Repository.UserRepository;
import com.e_commerceapp.E_Commerce.model.Users;

@Component 
public class Authutil {
    @Autowired 
    UserRepository userRepository;

    public String LogginuserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> user = userRepository.findByusername(authentication.getName());
        if(user.isEmpty()){
            throw new ApiException("user not found");
        }
        String email = user.get().getEmail();
        return email;


    }
    public Users logginuser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> user = userRepository.findByusername(authentication.getName());
        if(user.isEmpty()){
            throw new ApiException("user not found");
        }
       return user.get();

    }
    public Long logginuserid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Users> user = userRepository.findByusername(authentication.getName());
        if(user.isEmpty()){
            throw new ApiException("user not found");
        }
       return user.get().getUserid();
    }



    
}
