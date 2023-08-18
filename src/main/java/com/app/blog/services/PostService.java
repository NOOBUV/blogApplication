package com.app.blog.services;

import com.app.blog.payloads.Dto.PostDto;
import com.app.blog.payloads.PostResponse;

public interface PostService {
//    create
    PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
//    update
    PostDto updatePost(PostDto postDto, Integer postId);
//    delete
    void deletePost(Integer postId);
//    get
    PostDto getPostById(Integer postId);
//    getAll
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrd);
//    get all posts by category
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrd);
//    get all posts by user
    PostResponse getPostsByUser(Integer userId,Integer pageNumber, Integer pageSize, String sortBy, String sortOrd);
//    search posts
    PostResponse searchPosts(String keyword,Integer pageNumber,Integer pageSize,String sortBy, String sortOrd);
}
