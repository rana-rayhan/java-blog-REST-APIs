package com.blog.controller;

import com.blog.payload.ApiResponse;
import com.blog.payload.CommentDto;
import com.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post-id/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable("postId") Integer postId) {
        CommentDto createdComment = commentService.createComment(comment, postId);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{cId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("cId") Integer cId) {
        commentService.deleteComment(cId);
        return new ResponseEntity<>(new ApiResponse("comment deleted", true), HttpStatus.OK);
    }
}
