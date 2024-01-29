package com.blog.app.apis.services;


import com.blog.app.apis.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //create
   CategoryDto createCategory(CategoryDto categoeryDto);

    //update
      CategoryDto updateCategory(CategoryDto categoeryDto,Integer categoryId);

    //delete
      void deleteCategory(Integer categoryId);

      //get
      CategoryDto getCategoryById(Integer categoryId);

   //getAll
    List<CategoryDto> getAllCategory();


}
