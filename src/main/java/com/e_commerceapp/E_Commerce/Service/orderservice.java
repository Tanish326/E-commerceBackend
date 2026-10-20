package com.e_commerceapp.E_Commerce.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.e_commerceapp.E_Commerce.model.*;
import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Payload.OrderDto;
import com.e_commerceapp.E_Commerce.Payload.OrderItemDto;
import com.e_commerceapp.E_Commerce.Payload.OrderRequestDto;
import com.e_commerceapp.E_Commerce.Payload.PaymentDto;
import com.e_commerceapp.E_Commerce.Payload.ProductDto;
import com.e_commerceapp.E_Commerce.Repository.AddressRepository;
import com.e_commerceapp.E_Commerce.Repository.CartRepository;
import com.e_commerceapp.E_Commerce.Repository.OrderItemrepo;
import com.e_commerceapp.E_Commerce.Repository.OrderRepository;
import com.e_commerceapp.E_Commerce.Repository.PaymentRepo;
import com.e_commerceapp.E_Commerce.Repository.ProductRepo;
import com.e_commerceapp.E_Commerce.Repository.UserRepository;
import com.e_commerceapp.E_Commerce.utils.Authutil;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.*;
@Service 
public class orderservice {
    @Autowired 
    private ProductRepo productRepo;
    @Autowired 
    private OrderRepository orderRepository;
    @Autowired 
    private PaymentRepo paymentRepo;
    @Autowired 
    private AddressRepository addressRepository;
    @Autowired 
    private Authutil authutil;
    @Autowired 
    private ModelMapper modelMapper;
    @Autowired 
    OrderItemrepo orderItemrepo;
    @Autowired 
    private CartRepository cartRepository;
    @Transactional 
    public OrderDto placeorder(OrderRequestDto orderRequestDto,String paymentmethod){
        Cart cart = cartRepository.findByemail(authutil.LogginuserEmail());
        if(cart==null){
            throw new ApiException("no cart avaialable");
        }
        List<CartItems> cartitems = cart.getCartItems();
         Optional<Address> optionaladdress = addressRepository.findById(orderRequestDto.getAddressid());
         Address address = optionaladdress.get();
         Order order = new Order();
         order.setEmail(authutil.LogginuserEmail());
         order.setAddress(address);
         order.setOrderStatus("Placed");
         order.setOrderat(LocalDate.now());
         order.setTotalamount(cart.getTotalPrice());
        
         Payment payment = new Payment(orderRequestDto.getPaymentMethod(),orderRequestDto.getPgPayementId(),orderRequestDto.getPgname(),orderRequestDto.getPgstatus(),orderRequestDto.getPgResponsemessage());
         order.setPayment(payment);
         payment.setOrder(order);
         paymentRepo.save(payment);
        Order savedorder = orderRepository.save(order);
        List<OrderItems> orderItems = new ArrayList<>();
        if(cartitems.isEmpty()){
            throw new ApiException("No catitem");
        }
        for(CartItems cartItems2 : cartitems ){
            OrderItems orderItem = new OrderItems();
            orderItem.setDiscount(cartItems2.getDiscount());
            orderItem.setQuantity(cartItems2.getQuantities());
            orderItem.setProduct(cartItems2.getProduct());
            orderItem.setOrder(savedorder);
            orderItem.setProductprice(cartItems2.getProduct().getPrice());
            orderItems.add(orderItem);
        }
        orderItemrepo.saveAll(orderItems);
        cart.getCartItems().forEach(item->{
            Long quantity = item.getQuantities();
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - quantity);
            productRepo.save(product);

        });
        OrderDto orderDto = modelMapper.map(savedorder, OrderDto.class);
        orderItems.forEach(item->{
            orderDto.getOrderitemdto().add(modelMapper.map(item,OrderItemDto.class));
        });
       return orderDto;
    }
    
}
