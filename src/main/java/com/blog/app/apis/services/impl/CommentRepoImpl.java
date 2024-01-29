package com.blog.app.apis.services.impl;

import com.blog.app.apis.entities.Comment;
import com.blog.app.apis.entities.Post;
import com.blog.app.apis.exceptions.ResourceNotFoundException;
import com.blog.app.apis.payloads.CommentDto;
import com.blog.app.apis.repository.CommentRepo;
import com.blog.app.apis.repository.PostRepo;
import com.blog.app.apis.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentRepoImpl implements CommentService {


    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;


    @Autowired
    private ModelMapper modelMapper;



    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post","postId",postId));

        Comment comment=this.modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        Comment uploadComment=this.commentRepo.save(comment);
        return this.modelMapper.map(uploadComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("commnt","commentId",commentId));
        this.commentRepo.delete(comment);

    }
}
