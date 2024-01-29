package com.blog.app.apis.services.impl;

import com.blog.app.apis.entities.Category;
import com.blog.app.apis.exceptions.ResourceNotFoundException;
import com.blog.app.apis.payloads.CategoryDto;
import com.blog.app.apis.repository.CategoryRepo;
import com.blog.app.apis.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoeryDto) {

      Category category=  this.modelMapper.map(categoeryDto, Category.class);
      Category addedCategory=this.categoryRepo.save(category);
        return this.modelMapper.map(addedCategory,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoeryDto, Integer categoryId) {
      Category category=this.categoryRepo.findById(categoryId)
              .orElseThrow(()->
                      new ResourceNotFoundException("category","category_id",categoryId));

      category.setCategoryTitle(categoeryDto.getCategoryTitle());
      category.setCategoryDescription(categoeryDto.getCategoryDescription());

      Category updatedCategory=this.categoryRepo.save(category);
      CategoryDto categoeryDto1=this.modelMapper.map(updatedCategory,CategoryDto.class);
        return categoeryDto1;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("category","category_id",categoryId));

        this.categoryRepo.delete(category);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
      Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->
              new ResourceNotFoundException("category","ctaegory_id",categoryId));

        return    this.modelMapper.map(category,CategoryDto.class);

    }

    @Override
    public List<CategoryDto> getAllCategory() {

        List<Category> categories=this.categoryRepo.findAll();

      List<CategoryDto> categoeryDtos=  categories.stream().map((category)->
                this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
        return categoeryDtos;
    }
}
