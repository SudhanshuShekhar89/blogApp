package com.blog.app.apis.services;

import com.blog.app.apis.payloads.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {


    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);





}
