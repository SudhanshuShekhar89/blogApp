package com.blog.app.apis.services;

import com.blog.app.apis.payloads.PostDto;
import com.blog.app.apis.payloads.PostResponse;

import java.util.List;

public interface PostService {

     //create
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    //Update
    PostDto updatePost(PostDto postDto,Integer postId);

    //delete Post
    void deletePost(Integer postid);

    //get All Post
   PostResponse getAllPost(Integer pagNumber, Integer pageSize,String sorBy,String sortDir);
//    List<PostDto> getAllPost(Integer pagNumber,Integer pageSize);


    //get post BY id
    PostDto getPostById(Integer postId);

    //get all post by category
    List<PostDto>getAllPostByCategory(Integer categoryId);

    // get all post BY user
    List<PostDto> getAllPostByUser(Integer userId);

    //search post by keyWod

    List<PostDto> searchPost(String keyword);



}
