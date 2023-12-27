package com.blog.repositories;

import com.blog.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Categories, Integer> {
}
