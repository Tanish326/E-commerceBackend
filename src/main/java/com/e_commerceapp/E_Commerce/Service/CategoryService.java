package com.e_commerceapp.E_Commerce.Service;

import com.e_commerceapp.E_Commerce.Exceptions.ApiException;
import com.e_commerceapp.E_Commerce.Exceptions.ResourceNotFoundException;
import com.e_commerceapp.E_Commerce.Payload.CategoryDto;
import com.e_commerceapp.E_Commerce.Payload.Categoryresponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.List;
import com.e_commerceapp.E_Commerce.Repository.CategoryRepository;
import com.e_commerceapp.E_Commerce.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
@Service 
public class CategoryService {
    @Autowired 
    private CategoryRepository categorepository;
    @Autowired
    private ModelMapper modelMapper;
    public Categoryresponse GetAllCategory(Integer pagesize, Integer pageno,String Sortedby, String Sortedorder){
     Sort sortbyandorder = Sortedorder.equalsIgnoreCase("asc")
        ? Sort.by(Sortedby).ascending()
        : Sort.by(Sortedby).descending();
      Pageable  pagedetails =  PageRequest.of(pageno,pagesize,sortbyandorder);
      Page<Category> categpage = categorepository.findAll(pagedetails);
       List<Category> categories =  categpage.getContent();
       if(categories.isEmpty()){
        throw new ApiException("No CATEGORY EXIST");
       }
       List<CategoryDto> categoryDtos = categories.stream()
    .map(c -> modelMapper.map(c, CategoryDto.class))
    .toList();
    Categoryresponse categoryresponse  = new Categoryresponse();
    categoryresponse.setContent(categoryDtos);
    categoryresponse.setPagenumber(categpage.getNumber());
    categoryresponse.setPagesize(categpage.getSize());
   categoryresponse.setTotalelements(categpage.getTotalElements());
   categoryresponse.setTotalpages(categpage.getTotalPages());
   categoryresponse.setLastpage(categpage.isLast());
    return categoryresponse;
       
    }
    public CategoryDto CreateCategory(CategoryDto category){
          Category category1 = categorepository.findCategoryByName(category.getName());
          if(category1!=null){
            throw new ApiException("category with this name already exist");
          }
          Category categ = modelMapper.map(category,Category.class);
        Category savedCategory =   categorepository.save(categ);
       CategoryDto savedCategoryDto = modelMapper.map(savedCategory,CategoryDto.class);
       return savedCategoryDto;
    }
    public CategoryDto DeleteCategory(Long id){
       Optional<Category> op =   categorepository.findById(id);
       if(op.isEmpty()){
         throw new ResourceNotFoundException("Category", "categoryid", "id", id);
       }
       Category category = op.get();
       CategoryDto categdto =   modelMapper.map(category,CategoryDto.class);
        categorepository.deleteById(id);
       return categdto;
        
    }
    public CategoryDto updatecategory(CategoryDto category,long id){
       Optional<Category> op =  categorepository.findById(id);
       if(op.isEmpty()){
        throw new ResourceNotFoundException("Category", "Id", "id", id);
       }

        Category existingcategory = op.get();
        Category newcateg = modelMapper.map(category,Category.class);
        existingcategory.setDescription(newcateg.getDescription());
        existingcategory.setName(newcateg.getName());
        categorepository.save(existingcategory);
       return modelMapper.map(existingcategory,CategoryDto.class);


    }
    public  ResponseEntity<Category> GetCategory(String Name){
       Category category =  categorepository.findCategoryByName(Name);
       if(category==null){
        throw new ApiException("No PRODUCT EXIST"+ " with" + " Name " + Name);
       }
       return ResponseEntity.ok(category);
    }
}