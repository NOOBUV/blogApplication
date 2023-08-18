package com.app.blog.controllers;

import com.app.blog.payloads.ApiResponse;
import com.app.blog.payloads.Dto.CategoryDto;
import com.app.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Validated @RequestBody CategoryDto categoryDto) {
        CategoryDto createCat = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCat, HttpStatus.CREATED);
    }
//    update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Validated @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {
        CategoryDto category = this.categoryService.updateCategory(categoryDto,categoryId);
        return ResponseEntity.ok(category);
    }
//    delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted Successfully",true), HttpStatus.OK);
    }
//    get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getAllUsers(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(this.categoryService.getCategory(categoryId));
    }
//    get all
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllUsers() {
        return ResponseEntity.ok(this.categoryService.getAllCategories());
    }
}
