package com.blog.services;

import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostServices {
    // create post
    PostDto createPost(PostDto post, Integer user_id, Integer cat_id);

    // read all post
    List<PostDto> getPosts();

    // read all post
    PostDto getPost(int postId);

    // read single post
    PostResponse getAllPostPagination(Integer pageNum, Integer pageSize, String sortBy, String byDir);

    // update post
    PostDto updatePost(PostDto postDto, Integer post_id);

    // delete post
    void deletePost(Integer post_id);

    // get post by category_id
    List<PostDto> getPostByCategory(Integer cat_id);

    // get post by user_id
    List<PostDto> getPostByUser(Integer cat_id);

    // search posts by keyword
    List<PostDto> searchPost(String keyword);

}
