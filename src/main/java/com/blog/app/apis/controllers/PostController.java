package com.blog.app.apis.controllers;


import com.blog.app.apis.config.AppConstants;
import com.blog.app.apis.payloads.ApiResponse;
import com.blog.app.apis.payloads.PostDto;
import com.blog.app.apis.payloads.PostResponse;
import com.blog.app.apis.services.FileService;
import com.blog.app.apis.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
     private String path;


    //create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDto createdPost=this.postService.createPost(postDto,userId,categoryId);

        return  new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);

    }


    //get by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>>getPostByUser(@PathVariable Integer userId){
        List<PostDto> posts=this.postService.getAllPostByUser(userId);
        return  new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }

    //get by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
        List<PostDto>posts=this.postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
     }


     //get All post
        @GetMapping("/posts")
         public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){

       PostResponse allPost=this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
       return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
    }

    //get post detail by id
     @GetMapping("/posts/{postId}")
     public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId ){
        PostDto postDto=this.postService.getPostById(postId);
        return  new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
}

     //delete Post
     @DeleteMapping("/posts/{postId}")
     public ApiResponse deletePost(@PathVariable Integer postId){
     this.postService.deletePost(postId);
     return  new ApiResponse("deleted Succesfully",true);
     }


   //update
    @PutMapping("/posts/{postId}")
    public  ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
     PostDto post=this.postService.updatePost(postDto,postId);
     return  new ResponseEntity<PostDto>(post,HttpStatus.OK);
    }


    //search
    @GetMapping("/posts/search/{keyword}")
    public  ResponseEntity<List<PostDto>>searchPostByTitle(  @PathVariable("keyword") String  keyword){
        List<PostDto> post=this.postService.searchPost(keyword);
        return  new ResponseEntity<List<PostDto>>(post,HttpStatus.OK);
    }

    //postImage upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto>uploadPostImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {

       String fileName= this.fileService.uploadImage(path,image);
       PostDto postDto=this.postService.getPostById(postId);
       postDto.setImageName(fileName);
      PostDto updatePost= this.postService.updatePost(postDto,postId);
      return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }

    //method to serve file
    @GetMapping(value = "post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public  void downLoadImage(
            @PathVariable("imageName") String  imageName,
            HttpServletResponse response
    )throws IOException{
        InputStream resource=this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }


}
