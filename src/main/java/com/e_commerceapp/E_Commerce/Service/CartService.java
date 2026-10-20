package com.e_commerceapp.E_Commerce.Service;

import java.util.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.e_commerceapp.E_Commerce.model.*;
import com.e_commerceapp.E_Commerce.utils.Authutil;
import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Exceptions.ResourceNotFoundException;
import com.e_commerceapp.E_Commerce.Payload.CartDto;
import com.e_commerceapp.E_Commerce.Payload.CartItemdto;
import com.e_commerceapp.E_Commerce.Payload.ProductDto;
import com.e_commerceapp.E_Commerce.Repository.CartItemRepository;
import com.e_commerceapp.E_Commerce.Repository.CartRepository;
import com.e_commerceapp.E_Commerce.Repository.ProductRepo;
import com.e_commerceapp.E_Commerce.Repository.UserRepository;
import com.e_commerceapp.E_Commerce.config.AppConfig;
@Service 
public class CartService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    @Autowired 
    ProductRepo productRepo;
    @Autowired 

    private CartRepository cartRepository;
    @Autowired 
    private CartItemRepository cartItemRepository;
    @Autowired 
    private Authutil authutil;
   
@Transactional
public CartDto addproduct(Long productid, Long quantity) {

    if (quantity == null || quantity <= 0) {
        throw new ApiException("Quantity must be greater than zero");
    }

    Cart cart = createCart();

    Product product = productRepo.findById(productid)
            .orElseThrow(() -> new ApiException("Product not found"));

    Optional<CartItems> existingItem =
            cartItemRepository.finditemByCartIdandProductId(
                    cart.getCartId(), productid);

    long alreadyInCart = existingItem
            .map(CartItems::getQuantities)
            .orElse(0L);

    if (alreadyInCart + quantity > product.getQuantity()) {
        throw new ApiException("Insufficient stock");
    }

    CartItems cartItem;

    if (existingItem.isPresent()) {
        // Update the existing cart item
        cartItem = existingItem.get();
        cartItem.setQuantities(alreadyInCart + quantity);
    }  else {
    cartItem = new CartItems();
    cartItem.setCart(cart);
    cartItem.setProduct(product);
    cartItem.setQuantities(quantity);
    cartItem.setDiscount(product.getDiscount());

    cart.getCartItems().add(cartItem);
}

  cartItemRepository.save(cartItem);

double addedPrice = product.getSpecialprice() * quantity;
cart.setTotalPrice(cart.getTotalPrice() + addedPrice);

cartRepository.save(cart);

List<ProductDto> productDtos = cart.getCartItems()
        .stream()
        .map(item ->{ ProductDto productDto = modelMapper.map(
                item, ProductDto.class);
            productDto.setQuantity(cartItem.getQuantities());
            return productDto;
        }
        
    )
        .toList();

CartDto cartDto = modelMapper.map(cart, CartDto.class);
cartDto.setProductDtos(productDtos);

return cartDto;
}

private Cart createCart() {

    Cart cart = cartRepository.findByemail(
            authutil.LogginuserEmail());

    if (cart != null) {
        return cart;
    }

    Cart newCart = new Cart();
    newCart.setUsers(authutil.logginuser());
    newCart.setTotalPrice(0.0);

    return cartRepository.save(newCart);
}
public List<CartDto> getallcarts(){
    List<Cart> carts = cartRepository.findAll();
    if(carts.isEmpty()){
        throw new ApiException("no carts founded");
    }
    List<CartDto> cartDtos = carts.stream().map(cart-> { CartDto cartdtos = modelMapper.map(cart, CartDto.class);
       List<ProductDto> productDtos = cart.getCartItems().stream().map(item->modelMapper.map(item, ProductDto.class)).toList();
          cartdtos.setProductDtos(productDtos);
        return cartdtos;

}).toList();
    return cartDtos;

}
public CartDto getuserscarts(){
    Optional<Users> user = userRepository.findById(authutil.logginuserid());
    if(user.isEmpty()){
        throw new ApiException("User not found");
    }
     Cart cart = cartRepository.findByusers(user.get());
     if(cart==null){
        throw new ApiException("No Cart found");
     }
    List<ProductDto> productDtos =  cart.getCartItems().stream().map(c->modelMapper.map(c, ProductDto.class)).toList();

     CartDto cartDto = modelMapper.map(cart, CartDto.class);
     cartDto.setProductDtos(productDtos);
     return  cartDto;
     
    


}
public CartDto updateProductQuantity(Long quantity, Long productId) {

    Cart cart = cartRepository.findByemail(authutil.LogginuserEmail());

    if (cart == null) {
        throw new ApiException("Cart not found");
    }


    

    Product product = productRepo.findById(productId)
            .orElseThrow(() -> new ApiException("Product not found"));

    if (product.getQuantity() <= 0) {
        throw new ApiException(
                product.getProductName() + " is out of stock"
        );
    }

    CartItems cartItem = cartItemRepository
            .finditemByCartIdandProductId(cart.getCartId(), productId)
            .orElseThrow(() -> new ApiException(
                    "Product does not exist in the cart"
            ));

    Long newQuantity =
            cartItem.getQuantities() + quantity;
    if(newQuantity==0){
         cartItemRepository.deleteById(cartItem.getCarditemId());
        throw new ApiException("no product exist");
    }
    // Check total requested quantity against stock
    if (newQuantity > product.getQuantity()) {
        throw new ApiException("Not enough stock");
    }

    // Update cart item quantity
    cartItem.setQuantities(newQuantity);

    // Increase cart total only for the additional quantity
    cart.setTotalPrice(
            cart.getTotalPrice()
            + (quantity * product.getPrice())
    );

    cartItemRepository.save(cartItem);
    cart.setTotalPrice(cart.getTotalPrice() + (cartItem.getQuantities()*cart.getTotalPrice()));
    cartRepository.save(cart);

    CartDto cartDto = modelMapper.map(cart, CartDto.class);

    List<ProductDto> productDtos = cart.getCartItems()
            .stream()
            .map(c ->{ ProductDto productDto = modelMapper.map(c.getProduct(), ProductDto.class);
                productDto.setQuantity(c.getQuantities());
                return productDto;
            })
            .toList();

    cartDto.setProductDtos(productDtos);

    return cartDto;
}
public void DeleteProductInCart(Long Productid){
        Cart cart = cartRepository.findByemail(authutil.LogginuserEmail());
        if(cart==null){
            throw new ApiException("cart doesnot exist");
        }
        Optional<CartItems> cartItems = cartItemRepository.finditemByCartIdandProductId(cart.getCartId(),Productid);
        if(cartItems.isEmpty()){
            throw new ApiException("no product found in cart");
        }
        cartItemRepository.deleteByCartIdandProductId(cart.getCartId(),Productid);

         cart.setTotalPrice(cart.getTotalPrice() - (cartItems.get().getQuantities()*cartItems.get().getProduct().getPrice()));

  cartRepository.save(cart);
        
}
public  void Updatecart(Long productid,Long cartid){
     Optional<Cart> cart = cartRepository.findById(cartid);
    List<ProductDto> productDtos = cart.get().getCartItems().stream().map(c->{
        ProductDto productDto = modelMapper.map(c.getProduct(), ProductDto.class);
         productDto.setQuantity(c.getQuantities());
        return productDto;

     }).toList();
     CartDto cartDto = new CartDto();
     cartDto.setProductDtos(productDtos);
     
     
   
}


}
