package com.blog.controller;

import com.blog.config.AppConstants;
import com.blog.payload.PostDto;
import com.blog.payload.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostServices;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostServices postServices;

    @Autowired
    private FileService fileService;

    @Value("project.image")
    private String path;

    @PostMapping("/user/{user_id}/category/{cat_id}")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
                                              @PathVariable("user_id") Integer user_id,
                                              @PathVariable("cat_id") Integer cat_id) {
        PostDto postDto1 = postServices.createPost(postDto, user_id, cat_id);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtoList = postServices.getPosts();
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable("id") Integer id) {
        PostDto post = postServices.getPost(id);
        return new ResponseEntity<>(post, HttpStatus.FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Integer id) {
        PostDto post = postServices.updatePost(postDto, id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PostResponse> getPost(@RequestParam(value = "pageNum", defaultValue = AppConstants.pageNumber, required = false) Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = AppConstants.pageSize, required = false) Integer pageSize,
                                                @RequestParam(value = "sortBy", defaultValue = AppConstants.sortBy, required = false) String sortBy,
                                                @RequestParam(value = "byDir", defaultValue = AppConstants.sortDir, required = false) String byDir) {
        PostResponse postDtoList = postServices.getAllPostPagination(pageNum, pageSize, sortBy, byDir);

        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") int id) {
        postServices.deletePost(id);
        return new ResponseEntity<>("Post is deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> fetchPostByCategory(@PathVariable("id") Integer id) {

        List<PostDto> postDtoList = postServices.getPostByCategory(id);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PostDto>> fetchPostByUser(@PathVariable("id") Integer id) {

        List<PostDto> postDtoList = postServices.getPostByUser(id);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<PostDto>> fetPostByTitle(@PathVariable("title") String title) {
        List<PostDto> postDtoList = postServices.searchPost(title);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }


    // image upload
    @PostMapping("/image/{postId}")
    public ResponseEntity<PostDto> uploadImages(@RequestParam("image") MultipartFile image,
                                                @PathVariable("postId") int postId) throws IOException {
        PostDto post = postServices.getPost(postId);
        String imageName = fileService.uploadImage(path, image);
        post.setImage(imageName);
        PostDto postDto = postServices.updatePost(post, postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    // get image by image name
    @GetMapping("/download-image/{imageName}")
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
