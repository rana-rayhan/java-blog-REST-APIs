package com.blog.services;

import com.blog.entities.Categories;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.repositories.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoryServicesImpl implements CategoryServices {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Categories category = modelMapper.map(categoryDto, Categories.class);
        categoryRepo.save(category);

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(int categoryId) {
        Categories cat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));

        return modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Categories> catList = categoryRepo.findAll();
        List<CategoryDto> catDtoList = catList.stream().map((cat) -> modelMapper.map(cat, CategoryDto.class)).toList();
        return catDtoList;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int categoryId) {
        Categories findCat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        findCat.setCategoryTitle(categoryDto.getCategoryTitle());
        findCat.setCategoryDesc(categoryDto.getCategoryDesc());

        categoryRepo.save(findCat);
        return modelMapper.map(findCat, CategoryDto.class);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Categories cat = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
        categoryRepo.delete(cat);
    }
}
