package com.blog.payload;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
    private int id;
    @Size(min = 3, message = "post title must be at least 3 character")
    private String title;
    private String content;
    private String image;
    private Date addedDate;

    private CategoryDto categories;
    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();
}
