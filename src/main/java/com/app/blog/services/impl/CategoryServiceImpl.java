package com.app.blog.services.impl;

import com.app.blog.entities.Category;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.Dto.CategoryDto;
import com.app.blog.repositories.CategoryRepo;
import com.app.blog.services.CategoryService;
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
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category addedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updateCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(updateCat,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
        return this.modelMapper.map(cat,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoriesDto = categories.stream().
                map(category -> this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
        return categoriesDto;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));
        this.categoryRepo.delete(cat);
    }
}
