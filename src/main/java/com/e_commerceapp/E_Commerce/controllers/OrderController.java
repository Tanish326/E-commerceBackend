package com.e_commerceapp.E_Commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerceapp.E_Commerce.Payload.OrderDto;
import com.e_commerceapp.E_Commerce.Payload.OrderRequestDto;
import com.e_commerceapp.E_Commerce.Service.orderservice;

@RequestMapping("/api")
@RestController 
public class OrderController {
    @Autowired 
    private orderservice orderservice;

    @PostMapping("/public/placeorder/{paymentmethod}")
    public ResponseEntity<OrderDto> Placeorder(@RequestBody OrderRequestDto orderRequestDto,@PathVariable String paymentmethod){
        
        OrderDto orderDto = orderservice.placeorder(orderRequestDto,paymentmethod);
        return ResponseEntity.ok(orderDto);
    }
    
}
