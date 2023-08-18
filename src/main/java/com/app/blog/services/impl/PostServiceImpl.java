package com.app.blog.services.impl;

import com.app.blog.entities.Category;
import com.app.blog.entities.Post;
import com.app.blog.entities.User;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.Dto.PostDto;
import com.app.blog.payloads.PostResponse;
import com.app.blog.repositories.CategoryRepo;
import com.app.blog.repositories.PostRepo;
import com.app.blog.repositories.UserRepo;
import com.app.blog.services.PostService;
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
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User"," id ",userId));
        Category cat = this.categoryRepo.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category","Category Id",categoryId));

        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(cat);
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","post id",postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        this.postRepo.deleteById(postId);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortOrd) {
//        first we need a pageable object
        Pageable p = PageRequest.of(pageNumber,pageSize,getSort(sortBy,sortOrd));
        Page<Post> pagePost= this.postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream().map(post -> this.modelMapper.
                map(post,PostDto.class)).collect(Collectors.toList());
        return returnPostResponse(pagePost,postDtos);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize,String sortBy, String sortOrd) {
        Pageable p = PageRequest.of(pageNumber,pageSize,getSort(sortBy,sortOrd));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","category id",categoryId));
        Page<Post> pagePost = this.postRepo.findByCategory(category,p);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.
                map(post,PostDto.class)).collect(Collectors.toList());
        return returnPostResponse(pagePost,postDtos);
    }

    @Override
    public PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize,String sortBy, String sortOrd) {
        Pageable p = PageRequest.of(pageNumber,pageSize,getSort(sortBy,sortOrd));
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
        Page<Post> pagePost = this.postRepo.findByUser(user,p);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.
                map(post,PostDto.class)).collect(Collectors.toList());
        return returnPostResponse(pagePost,postDtos);
    }

    @Override
    public PostResponse searchPosts(String keyword,Integer pageNumber,Integer pageSize,String sortBy, String sortOrd) {
        Pageable p = PageRequest.of(pageNumber,pageSize,getSort(sortBy,sortOrd));
        Page<Post> pagePost = this.postRepo.searchByTitle("%" + keyword + "%",p);
        List<Post> posts = pagePost.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.
                map(post,PostDto.class)).collect(Collectors.toList());
        return returnPostResponse(pagePost,postDtos);
    }

    private PostResponse returnPostResponse(Page<Post> pagePost,List<PostDto> postDtos) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    private Sort getSort(String sortBy, String sortOrd) {
        Sort sort = null;
        if (sortOrd.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        return sort;
    }
}
