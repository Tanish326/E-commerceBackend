package com.e_commerceapp.E_Commerce.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collector;
import jakarta.transaction.Transactional;
import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Exceptions.ResourceNotFoundException;
import com.e_commerceapp.E_Commerce.Payload.AddressDto;
import com.e_commerceapp.E_Commerce.Repository.AddressRepository;
import com.e_commerceapp.E_Commerce.Repository.UserRepository;
import com.e_commerceapp.E_Commerce.model.Address;
import com.e_commerceapp.E_Commerce.model.Users;
import com.e_commerceapp.E_Commerce.utils.Authutil;

import jakarta.transaction.Transactional;

@Service
public class Addressservice {
     @Autowired 
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired 
    private Authutil authutil;
    @Autowired 
    private UserRepository userRepository;

    @Transactional 
    public AddressDto createAddress(AddressDto addressdto){
        Optional<Users> users = userRepository.findById(authutil.logginuserid());
        if(users.isEmpty()){
            throw new ApiException("USER NOT FOUND");
        }
      Set<Address> addresses = users.get().getAddresses();
      Address address =  modelMapper.map(addressdto, Address.class);
      address.setUsers(users.get());
      addressRepository.save(address);
      addresses.add(address);
      users.get().setAddresses(addresses);
      userRepository.save(users.get());
      return modelMapper.map(address, AddressDto.class);
      
        
        
    }
    public List<AddressDto> GetAllAddresses(){
       List<Address> addresses =  addressRepository.findAll();
       if(addresses.isEmpty()){
        throw new ApiException("No addresses are there ");
       }
       List<AddressDto> addressDtos = addresses.stream().map(c-> modelMapper.map(c, AddressDto.class)).toList();
       return  addressDtos;
    }
    public List<AddressDto> GETaddressByuser(){
        Long userid = authutil.logginuserid();
        Optional<Users> user = userRepository.findById(userid);
        if(user.isEmpty()){
            throw new ApiException("User Not Found");
        }
         Set<Address> addresses = user.get().getAddresses();
       return   addresses.stream().map(c->modelMapper.map(c, AddressDto.class)).toList();

      


    }
    public AddressDto GetAddressByid(Long addressid){
       Optional<Address> address = addressRepository.findById(addressid);
       if(address.isEmpty()){
        throw new ResourceNotFoundException("address", "addressid", "id", addressid);

       }
       AddressDto addressDto = modelMapper.map(address.get(), AddressDto.class);
       return  addressDto;
    }
    public AddressDto updateAddressbyid(Long addressid,AddressDto addressDto){
        Optional<Users> user = userRepository.findById(authutil.logginuserid());
        if(user.isEmpty()){
            throw new ApiException("user not loginned");
        }
        Optional<Address> address = addressRepository.findById(addressid);
        if(address.isEmpty()){
            throw new ResourceNotFoundException("address", "addressid","id", addressid);
        }

        Address address2 = address.get();
         address2.setStreet(addressDto.getStreet());
    address2.setState(addressDto.getState());
    address2.setBuildingname(addressDto.getBuildingname());
    address2.setCity(addressDto.getCity());
    address2.setCountry(addressDto.getCountry());
    address2.setPincode(addressDto.getPincode());
    addressRepository.save(address2);
    user.get().getAddresses().removeIf(adress-> adress.getAddressid().equals(addressid));
    user.get().getAddresses().add(address2);
    userRepository.save(user.get());
    return modelMapper.map(address2, AddressDto.class);
      
         

    }
    @Transactional
    public void DeleteAddress(Long addressid){
        Optional<Users> user = userRepository.findById(authutil.logginuserid());
        if(user.isEmpty()){
            throw new ApiException("user not found");
        }
        Set<Address> addresses = user.get().getAddresses();
        
       Optional<Address> addressoptional = addressRepository.findById(addressid);
       if(addressoptional.isEmpty()){
        throw new ApiException("Address not found");
       }
        addresses.remove(addressoptional.get());
        addressRepository.deleteById(addressid);
        user.get().setAddresses(addresses);
        userRepository.save(user.get());

    }
    
}
