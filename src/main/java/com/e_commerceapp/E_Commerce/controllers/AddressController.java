package com.e_commerceapp.E_Commerce.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.modelmapper.ModelMapper;
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
import com.e_commerceapp.E_Commerce.Payload.AddressDto;
import com.e_commerceapp.E_Commerce.Repository.AddressRepository;
import com.e_commerceapp.E_Commerce.Repository.UserRepository;
import com.e_commerceapp.E_Commerce.Service.Addressservice;
import com.e_commerceapp.E_Commerce.utils.Authutil;

@RestController 
@RequestMapping("/api")
public class AddressController {
    @Autowired 
    private Addressservice addressservice;
    @Autowired 
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired 
    private Authutil authutil;
    @Autowired 
    private UserRepository userRepository;
    @PostMapping("/public/createaddress")
    public ResponseEntity<AddressDto> CreateAddress(@RequestBody AddressDto addressdto){
      AddressDto addressDto2 =  addressservice.createAddress(addressdto);
      return ResponseEntity.ok(addressDto2);
        
 
        


    }
    @GetMapping("/admin/getaddresses")
    public ResponseEntity<List<AddressDto>> GetallAddresses(){
        List<AddressDto> AddressDtos = addressservice.GetAllAddresses();
        return ResponseEntity.ok(AddressDtos);

    }
    @GetMapping("/public/getusersaddreses")
    public ResponseEntity<List<AddressDto>> getusersaddreses(){
        List<AddressDto> addressDtos = addressservice.GETaddressByuser();
        return ResponseEntity.ok(addressDtos);
    }
    @GetMapping("/public/getaddress/{addressid}")
    public ResponseEntity<AddressDto> getaddress(@PathVariable Long addressid){
        AddressDto addressDto = addressservice.GetAddressByid(addressid);
        return ResponseEntity.ok(addressDto);
    }
    @PutMapping("/public/updateaddress/{addressid}")
    public ResponseEntity<AddressDto> UpdateAddress(@PathVariable Long addressid,@RequestBody AddressDto addressDto){
        AddressDto addressDto2 = addressservice.updateAddressbyid(addressid,addressDto);
        return  ResponseEntity.ok(addressDto2);
        
    }
    @DeleteMapping("/public/deleteaddress/{addressid}")
    public ResponseEntity<String> DeleteAddress(@PathVariable Long addressid){
        addressservice.DeleteAddress(addressid);
       return ResponseEntity.ok("Deleted");
    }
    
}
