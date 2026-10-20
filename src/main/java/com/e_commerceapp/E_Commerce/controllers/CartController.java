package com.e_commerceapp.E_Commerce.controllers;

import java.util.*;
import com.e_commerceapp.E_Commerce.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.e_commerceapp.E_Commerce.model.*;
import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Exceptions.ResourceNotFoundException;
import com.e_commerceapp.E_Commerce.Payload.CartDto;
import com.e_commerceapp.E_Commerce.Payload.CartItemdto;
import com.e_commerceapp.E_Commerce.Payload.ProductDto;
import com.e_commerceapp.E_Commerce.Repository.CartItemRepository;
import com.e_commerceapp.E_Commerce.Repository.CartRepository;
import com.e_commerceapp.E_Commerce.model.Product;

import jakarta.transaction.Transactional;

@RestController 
@RequestMapping("/api")
public class CartController {
    @Autowired
    CartService cartservice;
    @Autowired 
    private CartItemRepository cartItemRepository;
    @Autowired 
    private CartRepository cartRepository;
    @PostMapping("/public/addproduct/{productid}/{quantity}")
    public ResponseEntity<CartDto> Addproduct(@PathVariable Long productid, @PathVariable Long quantity ){
        CartDto cartDto = cartservice.addproduct(productid,quantity);
        return ResponseEntity.ok(cartDto);
    }
    @GetMapping("/admin/getallcarts")
    public ResponseEntity<List<CartDto>> GetAllCarts(){
        List<CartDto> cartDtos = cartservice.getallcarts();
        return ResponseEntity.ok(cartDtos);
    }
    @GetMapping("/public/getusercart")
    public ResponseEntity<CartDto> getuserscart(){
       CartDto cartDto = cartservice.getuserscarts();
       return ResponseEntity.ok(cartDto);
    }
    @PutMapping("/public/updatecart/{productid}/{operation}")
    public ResponseEntity<CartDto> updateproductquanity(@PathVariable Long productid,@PathVariable String operation){
        CartDto cartDto = cartservice.updateProductQuantity(operation.equalsIgnoreCase("delete")?-1L:1L,productid);
        return ResponseEntity.ok(cartDto);

    }
    @Transactional 
    @DeleteMapping("/public/deleteproduct/{productid}")
    public ResponseEntity<String> deletecartitem(@PathVariable Long productid){
        cartservice.DeleteProductInCart(productid);
        return ResponseEntity.ok("deleted product");

    }
    
}
