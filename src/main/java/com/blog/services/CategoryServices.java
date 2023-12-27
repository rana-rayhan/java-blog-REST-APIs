package com.blog.services;

import com.blog.payload.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryServices {
    // create
    CategoryDto createCategory(CategoryDto categoryDto);

    // read single
    CategoryDto getCategory(int categoryId);

    // read all
    List<CategoryDto> getCategories();

    // update
    CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

    // delete
    void deleteCategory(int categoryId);
}
