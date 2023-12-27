package com.blog.repositories;

import com.blog.entities.Categories;
import com.blog.entities.Post;
import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategories(Categories categories);

    List<Post> findByTitleContaining(String title);
}
