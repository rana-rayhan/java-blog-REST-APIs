package com.blog.repositories;

import com.blog.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
}
