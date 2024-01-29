package com.blog.app.apis.controllers;

import com.blog.app.apis.payloads.ApiResponse;
import com.blog.app.apis.payloads.CategoryDto;
import com.blog.app.apis.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoreis")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;



    //post -> send to server
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createCategory= this.categoryService.createCategory(categoryDto);
        return  new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
    }

      //put-> update the data
    @PutMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
        CategoryDto  updateCategory=this.categoryService.updateCategory(categoryDto,categoryId);
        return  new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);

    }

      //delete ->delete data
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new  ResponseEntity<ApiResponse> (new ApiResponse("Category Deleted Succesfully",true),HttpStatus.OK);
    }


      //get -> feth All data
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
       return  ResponseEntity.ok( this.categoryService.getAllCategory());
    }

      //get -> fwtch data by id
    @GetMapping("/{categoryId}")
    public  ResponseEntity<CategoryDto> getUserById(@PathVariable Integer categoryId){
        return  ResponseEntity.ok(this.categoryService.getCategoryById(categoryId));
    }


}
