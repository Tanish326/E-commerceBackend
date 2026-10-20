package com.e_commerceapp.E_Commerce.controllers;

import java.util.stream.Collectors;

import com.e_commerceapp.E_Commerce.model.App_enum;
import com.e_commerceapp.E_Commerce.model.Role;
import com.e_commerceapp.E_Commerce.model.Users;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.e_commerceapp.E_Commerce.jwt.services.CustomUserDetails;
import java.util.*;
import jakarta.servlet.http.HttpServlet;

import org.springframework.security.core.AuthenticationException;

import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Payload.Loginrequest;
import com.e_commerceapp.E_Commerce.Payload.Loginresponse;
import com.e_commerceapp.E_Commerce.Payload.Signuprequest;
import com.e_commerceapp.E_Commerce.Repository.UserRepository;
import com.e_commerceapp.E_Commerce.Repository.roleRepository;
import com.e_commerceapp.E_Commerce.jwt.Jwtutils;

@RestController 
@RequestMapping("/api")
public class Authenticationcontroller {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
   @Autowired 
   private AuthenticationManager authenticationManager;
   @Autowired 
   private Jwtutils jwtutils;

   @Autowired 
   private roleRepository roleRepository;
    @PostMapping("/signin")
    public ResponseEntity<?> AuthenticateUser(@RequestBody Loginrequest loginrequest){
        Authentication authentication;
        try{
        authentication =  authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginrequest.getUsername(), loginrequest.getPassword())

          );


        }catch(AuthenticationException exception){
            final Map<String,Object> map = new HashMap<>();
            map.put("error", exception.getMessage());
            map.put("status",false);
            return new ResponseEntity<Object>(map,HttpStatus.NOT_FOUND);

        }
        SecurityContextHolder.getContext().setAuthentication( authentication );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
       String tokens = jwtutils.GeneratingJwtTokens(userDetails);
        List<String>roles = userDetails.getAuthorities().stream().map((item)->item.getAuthority()).collect(Collectors.toList());
        Loginresponse loginresponse = new Loginresponse(tokens,userDetails.getUsername(),roles);
        return ResponseEntity.ok(loginresponse);

    }
   
   
@PostMapping("/signup")
public ResponseEntity<?> registerUser(
        @Valid @RequestBody Signuprequest request) {

    // Check username
    if (userRepository.existsByusername(request.getUsername())) {
        throw new ApiException("Username already taken");
    }

    // Check email
    if (userRepository.existsByemail(request.getEmail())) {
        throw new ApiException("Email already exists");
    }

    // Create user
    Users users = new Users(
            request.getUsername(),
            request.getEmail(),
            passwordEncoder.encode(request.getPassword())
    );

    // Assign default ROLE_USER
    Role userRole = roleRepository
            .findByappenum(App_enum.ROLE_USER)
            .orElseThrow(() -> new ApiException("ROLE_USER not found"));

    Set<Role> roles = new HashSet<>();
    roles.add(userRole);

    users.setroles(roles);

    // Save user
    userRepository.save(users);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body("User registered successfully");
}

@GetMapping("/username")
public String GetUsername(Authentication authentication){
    if(authentication!=null){
        return authentication.getName();
    }else{
        return "";
    }
}




    
}
