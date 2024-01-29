package com.blog.app.apis.services.impl;

import com.blog.app.apis.entities.Category;
import com.blog.app.apis.entities.Post;
import com.blog.app.apis.entities.User;
import com.blog.app.apis.exceptions.ResourceNotFoundException;
import com.blog.app.apis.payloads.PostDto;
import com.blog.app.apis.payloads.PostResponse;
import com.blog.app.apis.repository.CategoryRepo;
import com.blog.app.apis.repository.PostRepo;
import com.blog.app.apis.repository.UserRepo;
import com.blog.app.apis.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;




    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user=this.userRepo.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("user","userId",userId));

        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("category","categoryId",categoryId));

        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post createPost=this.postRepo.save(post);
        return this.modelMapper.map(createPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","postId",postId));

post.setTitle(postDto.getTitle());
post.setContent(postDto.getContent());
post.setImageName(postDto.getImageName());

  Post updatedPost= this.postRepo.save(post);

        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postid) {
        Post post =this.postRepo.findById(postid).orElseThrow(()->new ResourceNotFoundException("post","post_id",postid));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {

        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else{
            sort=Sort.by(sortBy).descending();
        }

        Pageable p=PageRequest.of(pageNumber,pageSize, sort);

      Page<Post> pagePost=  this.postRepo.findAll(p);
      List<Post>allPost=pagePost.getContent();

       List<PostDto>postDtos=  allPost.stream().map((post )->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());


       PostResponse postResponse=new PostResponse();
       postResponse.setContent(postDtos);
       postResponse.setPageNumber(pagePost.getNumber());
       postResponse.setPageSize(pagePost.getSize());
       postResponse.setTotalElements(pagePost.getTotalElements());
       postResponse.setTotalPages(pagePost.getTotalPages());
       postResponse.setLastPage(pagePost.isLast());

       return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
       Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","post_id",postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {

        Category categoryid=this.categoryRepo.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("category","category_id",categoryId));
      List<Post>posts= this.postRepo.findByCategory(categoryid);

    List<PostDto>postDtos= posts.stream().map((post) ->this.modelMapper.map(post,PostDto.class) ).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->
                new ResourceNotFoundException("user" ,"user_id",userId));

        List<Post>posts=this.postRepo.findByUser(user);

        List<PostDto>postDtos= posts.stream().map((post)->
            this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
     public List<PostDto> searchPost(String keyword) {
     List<Post>posts= this.postRepo.findByTitleContaining(keyword);
     List<PostDto>listofPost=  posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return listofPost;
    }
}
