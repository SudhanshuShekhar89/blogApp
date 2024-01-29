package com.blog.app.apis.controllers;


import com.blog.app.apis.payloads.ApiResponse;
import com.blog.app.apis.payloads.CommentDto;
import com.blog.app.apis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId){
        CommentDto createComment=this.commentService.createComment(commentDto,postId);
        return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return  new ResponseEntity<ApiResponse>(new ApiResponse("comment Deleted successfullly",true),HttpStatus.OK);
    }
}
