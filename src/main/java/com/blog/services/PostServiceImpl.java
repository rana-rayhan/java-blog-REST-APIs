package com.blog.services;

import com.blog.entities.Categories;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.repositories.CategoryRepo;
import com.blog.repositories.PostRepo;
import com.blog.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostServices {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDto createPost(PostDto postData, Integer user_id, Integer cat_id) {
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user id ", user_id));
        Categories categories = categoryRepo
                .findById(cat_id).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", cat_id));

        Post post = modelMapper.map(postData, Post.class);
        post.setImage("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategories(categories);

        Post post1 = postRepo.save(post);
        return modelMapper.map(post1, PostDto.class);
    }

    @Override
    public List<PostDto> getPosts() {
        List<Post> postList = postRepo.findAll();
        List<PostDto> postDtoList = postList.stream().map((p) -> modelMapper.map(p, PostDto.class)).toList();

        return postDtoList;
    }

    @Override
    public PostDto getPost(int postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPostPagination(Integer pageNum, Integer pageSize, String sortBy, String byDir) {
        Sort sort = (byDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNum, pageSize, sort);
        Page<Post> posts = postRepo.findAll(p);

        List<Post> postList = posts.getContent();
        List<PostDto> postDtoList = postList.stream().map((post) -> modelMapper.map(post, PostDto.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDtoList(postDtoList);
        postResponse.setPageNum(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setLastPage(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer post_id) {
        Post post = postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", post_id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImage(postDto.getImage());
        Post post1 = postRepo.save(post);
        return modelMapper.map(post1, PostDto.class);
    }

    @Override
    public void deletePost(Integer post_id) {
        Post post = postRepo.findById(post_id).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", post_id));
        postRepo.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer cat_id) {
        Categories cat = categoryRepo.findById(cat_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", cat_id));
        List<Post> postList = postRepo.findByCategories(cat);
        List<PostDto> postDtoList = postList.stream().map((p) -> modelMapper.map(p, PostDto.class)).toList();

        return postDtoList;
    }

    @Override
    public List<PostDto> getPostByUser(Integer user_id) {
        User user = userRepo.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", user_id));
        List<Post> postList = postRepo.findByUser(user);
        List<PostDto> postDtoList = postList.stream().map((p) -> modelMapper.map(p, PostDto.class)).toList();

        return postDtoList;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> postList = postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtoList = postList.stream().map(p -> modelMapper.map(p, PostDto.class)).toList();
        return postDtoList;
    }
}
