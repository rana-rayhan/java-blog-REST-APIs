package com.blog.controller;

import com.blog.payload.CategoryDto;
import com.blog.services.CategoryServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

    // create
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto cat = categoryServices.createCategory(categoryDto);
        return new ResponseEntity<>(cat, HttpStatus.CREATED);
    }

    // read single
    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("id") int categoryId) {
        CategoryDto cat = categoryServices.getCategory(categoryId);
        return new ResponseEntity<>(cat, HttpStatus.FOUND);
    }

    // read all
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> catList = categoryServices.getCategories();
        return new ResponseEntity<>(catList, HttpStatus.FOUND);
    }

    // update
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("id") int categoryId) {
        CategoryDto updateCat = categoryServices.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updateCat, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int categoryId) {
        categoryServices.deleteCategory(categoryId);
        return new ResponseEntity<>("User is deleted Successfully", HttpStatus.OK);
    }

}
