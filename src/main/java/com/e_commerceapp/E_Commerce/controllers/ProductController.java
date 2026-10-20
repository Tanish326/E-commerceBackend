package com.e_commerceapp.E_Commerce.controllers;

import java.io.IOException;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.e_commerceapp.E_Commerce.Payload.ProductDto;
import com.e_commerceapp.E_Commerce.Payload.ProductResponse;
import com.e_commerceapp.E_Commerce.Repository.CategoryRepository;
import com.e_commerceapp.E_Commerce.Service.ProductService;
import com.e_commerceapp.E_Commerce.config.Appconstants;
import com.e_commerceapp.E_Commerce.model.Product;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api")
public class ProductController {

    @Autowired 
    private ProductService productService;

    @GetMapping("/Public/Categories/{Categoryid}/Products")
    public ResponseEntity<ProductResponse> GetAllProducts(@Valid @PathVariable Long Categoryid,@RequestParam(name = "Pageno",defaultValue =Appconstants.PAGE_NO,required = false) Integer Pageno,@RequestParam(name = "Pagesize",defaultValue = Appconstants.PAGE_SIZE,required = false ) Integer Pagesize,@RequestParam(name = "sortby") String sortby,@RequestParam(name = "sortingorder") String sortingorder){
        return productService.GetAllProductsByCategory(Categoryid,Pageno,Pagesize,sortingorder,sortby);

    }
    @PostMapping("/admin/Categories/{categoryid}/Product")
    public ResponseEntity<ProductDto> AddProduct(@Valid  @PathVariable Long categoryid, @RequestBody ProductDto prod ){
         return productService.createProduct(prod,categoryid);
    }
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getallproducts(){
        return productService.GetAllProducts();
    }
    @GetMapping("/public/products/{keyword}")
    public ResponseEntity<ProductResponse> getproductsbykeyword(@Valid @PathVariable String keyword){
        return productService.GetproductsbyKeyword(keyword);
    }
    @PutMapping("/admin/{ProductId}/product")
    public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable Long ProductId,@RequestBody ProductDto productDto){
        return productService.UpdateProduct(productDto, ProductId);
    }
    @DeleteMapping("/admin/deleteproduct/{productid}")
    public ResponseEntity<ProductDto> deleteProduct(@Valid @PathVariable Long productid){
           return productService.DeleteProduct(productid);
    }
    @PostMapping("/admin/uploadimage/{productid}")
    public ResponseEntity<ProductDto> Addprductimage(@Valid @PathVariable Long productid,@RequestBody MultipartFile file) throws IOException{
        return productService.AddImage(productid, file);
    }
}
