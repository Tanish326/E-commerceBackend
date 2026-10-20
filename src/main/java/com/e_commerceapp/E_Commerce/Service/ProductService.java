package com.e_commerceapp.E_Commerce.Service;

import com.e_commerceapp.E_Commerce.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Exceptions.ResourceNotFoundException;
import com.e_commerceapp.E_Commerce.Payload.CategoryDto;
import com.e_commerceapp.E_Commerce.Payload.ProductDto;
import com.e_commerceapp.E_Commerce.Payload.ProductResponse;
import com.e_commerceapp.E_Commerce.Repository.CartRepository;
import com.e_commerceapp.E_Commerce.Repository.CategoryRepository;
import com.e_commerceapp.E_Commerce.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Value;
@Service 
public class ProductService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired 
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired 
    private ModelMapper modelMapper;

    @Autowired 
    private CartService cartService;

    @Autowired 
    private FileService fileService;
     
    @Value("${project.path}")
    private String path;
    public ResponseEntity<ProductResponse>GetAllProductsByCategory(Long categid,Integer pageno,Integer pagesize,String sortorder,String sortedby){
    
     Optional<Category> categos = categoryRepository.findById(categid);
     if(categos.isEmpty()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found");
     }
      Sort sortbyandorder = sortorder.equalsIgnoreCase("asc")
        ? Sort.by(sortedby).ascending()
        : Sort.by(sortedby).descending();
     
     
     Pageable pagedetails = PageRequest.of(pageno,pagesize,sortbyandorder);
     Page<Product> productPage = productRepo.findByCategoryCategoryId(categid,pagedetails);
     List<Product> products = productPage.getContent();
    
    List<ProductDto> productDtos = products.stream().map(c -> modelMapper.map(c,ProductDto.class)).toList();

     ProductResponse productResponse = new ProductResponse();
     productResponse.setProductDtos(productDtos);
     productResponse.setPageSize(productPage.getSize());
     productResponse.setPages(productPage.getTotalPages());
     productResponse.setPageno(productPage.getNumber());
     productResponse.setIsLast(productPage.isLast());
     productResponse.setTotalitems(productPage.getNumberOfElements());
    
     return ResponseEntity.ok(productResponse);
    }
    public ResponseEntity<ProductResponse> GetAllProducts(){
      List<Product>prods =  productRepo.findAll();
      if(prods.isEmpty()){
        throw new ApiException("No product exist");
      }
     List<ProductDto> productDtos = prods.stream().map(c->modelMapper.map(c, ProductDto.class)).toList();
     ProductResponse productResponse = new ProductResponse();
     productResponse.setProductDtos(productDtos);
     return ResponseEntity.ok(productResponse);

    }
    public ResponseEntity<ProductDto> createProduct(ProductDto product,Long categoryId){
        Optional<Category> categ  = categoryRepository.findById(categoryId);
        if(categ.isEmpty()){
            throw new ResourceNotFoundException("Category", "id", "Categoryid", categoryId);
        }
        
        Product product2 = modelMapper.map(product, Product.class);
       double d = (product2.getDiscount()*0.01);
       double p = product2.getPrice();
       double sp = p - (d*p);
       product2.setSpecialprice(sp);
       product2.setQuantity(product.getQuantity());
       product2.setPrice(product.getPrice());
       product2.setCategory(categ.get());
       Product product3 = productRepo.save(product2);
       
       ProductDto productDto = modelMapper.map(product3,ProductDto.class);
       return ResponseEntity.ok(productDto);


        
    }
    public ResponseEntity<ProductResponse> GetproductsbyKeyword(String keyword){
      List<Product> prods =  productRepo.findByproductNameContainingIgnoreCase(keyword);
      if(prods.isEmpty()){
        throw new ApiException("No Products are found");
      }
      List<ProductDto> productDtos = prods.stream().map(c->modelMapper.map(c, ProductDto.class)).toList();
      ProductResponse productResponse = new ProductResponse();
      productResponse.setProductDtos(productDtos);
      return ResponseEntity.ok(productResponse);
    }
    public ResponseEntity<ProductDto> UpdateProduct(ProductDto product,Long productid){
         Optional<Product>prods = productRepo.findById(productid);
         if(prods.isEmpty()){
            throw new ApiException("NO PRODUCT EXIST");
         }
         Product proddb = modelMapper.map(prods.get(), Product.class);
           proddb.setDiscount(product.getDiscount());
           proddb.setPrice(product.getPrice());
           proddb.setProductName(product.getProductName());
           proddb.setQuantity(product.getQuantity());
           proddb.setSpecialprice(product.getSpecialprice());

              Product product2 = productRepo.save(proddb);
         ProductDto productDto = modelMapper.map(product2, ProductDto.class);
         Optional<Cart> cart = cartRepository.findCartByProductId(productid);
         if(cart.isEmpty()){
          throw new ApiException("no cart exist");
         }
         cartService.Updatecart(productid,cart.get().getCartId());
         return ResponseEntity.ok(productDto);

    }
    public ResponseEntity<ProductDto>DeleteProduct(Long productId){
        Optional<Product>prods = productRepo.findById(productId);
        if(prods.isEmpty()){
            throw new ResourceNotFoundException("Product","productid","id",productId);
        }
        productRepo.deleteById(productId);
        ProductDto productDto = modelMapper.map(prods.get(),ProductDto.class);
        return ResponseEntity.ok(productDto);
    }
   
    public ResponseEntity<ProductDto> AddImage(Long Productid,MultipartFile Image) throws IOException{
        Optional<Product>prod = productRepo.findById(Productid);
        if(prod.isEmpty()){
            throw new ApiException("No product exist");

        }
        
        String file = fileService.uploadimage(path,Image);
        Product productdb = prod.get();
        productdb.setImage(file);
        Product product = productRepo.save(productdb);
       ProductDto productDto = modelMapper.map(product, ProductDto.class);
       return ResponseEntity.ok(productDto);
    }
   
    
}
