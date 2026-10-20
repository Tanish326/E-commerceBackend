package com.e_commerceapp.E_Commerce.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Payload.CategoryDto;
import com.e_commerceapp.E_Commerce.Payload.Categoryresponse;
import com.e_commerceapp.E_Commerce.Service.*;
import com.e_commerceapp.E_Commerce.config.Appconstants;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.e_commerceapp.E_Commerce.model.Category;

import jakarta.validation.Valid;
@RestController 
@RequestMapping("/api")
public class CategoryController {
    private CategoryService service;

    public CategoryController(CategoryService service) {
         this.service = service;
    }

     @GetMapping("/Public/categories")
    public ResponseEntity<Categoryresponse> getAllCategory(@RequestParam(value = "pagesize" ,defaultValue = Appconstants.PAGE_SIZE,required = false) Integer pagesize, @RequestParam(value = "pageno",defaultValue = Appconstants.PAGE_NO,required = false) Integer pageno,
@RequestParam(value = "sortby",defaultValue = Appconstants.SORTING_BY,required = false) String sortby , @RequestParam(value = "Sortingorder",defaultValue = Appconstants.SORTING_ORDER,required = false) String Sortingorder){
        Categoryresponse categoryresponse = service.GetAllCategory(pagesize,pageno,sortby,Sortingorder);
        return ResponseEntity.ok(categoryresponse);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDto> CreateCategory(@Valid @RequestBody CategoryDto category){
        CategoryDto categdto = service.CreateCategory(category);
        return  ResponseEntity.ok(categdto);
    }
    @DeleteMapping("/admin/deletecategories/{id}")
    public ResponseEntity<CategoryDto> DeleteCategory(@PathVariable Long id){
          CategoryDto categoryDto = service.DeleteCategory(id);
          return ResponseEntity.ok(categoryDto);

    }
  @PutMapping("/admin/updatecategory/{categoryid}")
    public ResponseEntity<CategoryDto> UpdateCategory(@Valid @RequestBody CategoryDto category,@PathVariable long categoryid){
       CategoryDto categdto = service.updatecategory(category, categoryid);
       return ResponseEntity.ok(categdto);
    }
 @GetMapping("/Public/category/{Name}")
 public ResponseEntity<Category> GetCategorybyname(@Valid @PathVariable String Name){
     return service.GetCategory(Name);
 }
  
}
