package com.app.blog.controllers;

import com.app.blog.payloads.ApiResponse;
import com.app.blog.payloads.Dto.PostDto;
import com.app.blog.payloads.PostResponse;
import com.app.blog.services.FileService;
import com.app.blog.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import com.app.blog.config.AppConstants;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

//    create
    @Operation(summary = "Create a new post",
        description = "Create a new post associated with a user and a category.")
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
        PostDto newPost = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<PostDto>(newPost, HttpStatus.CREATED);
    }
//    get by user
    @Operation(summary = "Get posts by user",
        description = "Retrieve posts authored by a specific user.")
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
                                                       @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
                                                       @RequestParam(value = "sortOrd", defaultValue = "asc", required = false) String sortOrd) {
        PostResponse posts = this.postService.getPostsByUser(userId,pageNumber,pageSize,sortBy,sortOrd);
        return ResponseEntity.ok(posts);
    }
//    get by category
    @Operation(summary = "Get posts by category",
        description = "Retrieve posts belonging to a specific category.")
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "sortOrd", defaultValue = AppConstants.SORT_ORD, required = false) String sortOrd
    ) {
        PostResponse posts = this.postService.getPostsByCategory(categoryId,pageNumber,pageSize,sortBy,sortOrd);
        return ResponseEntity.ok(posts);
    }
//    get all posts
    @Operation(summary = "Get all posts", description = "Retrieve all posts.")
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortOrd", defaultValue = AppConstants.SORT_ORD, required = false) String sortOrd
            ) {
        PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortOrd);
        return ResponseEntity.ok(postResponse);
    }
//    get posts by keyword
    @Operation(summary = "find post", description = "search post with title")
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<PostResponse> searchPostByTitle(@PathVariable("keywords") String keywords,
                                                           @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "sortOrd", defaultValue = AppConstants.SORT_ORD, required = false) String sortOrd) {
        PostResponse postResponse = this.postService.searchPosts(keywords,pageNumber,pageSize,sortBy,sortOrd);
        return ResponseEntity.ok(postResponse);
    }
//    get by id
    @Operation(summary = "Get post by id")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(post,HttpStatus.OK);
    }

//    delete post
    @Operation(summary = "Delete post")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
        this.postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("post deleted successfully",true));
    }
//    update post
    @Operation(summary = "Update post")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId) {
        PostDto updatePost = this.postService.updatePost(postDto,postId);
        return ResponseEntity.ok(updatePost);
    }
//    upload image
    @Operation(summary = "Upload image",description = "upload image to a post")
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto,postId);
        return ResponseEntity.ok(updatePost);
    }
//    method to serve files
    @Operation(summary = "see image",description = "see image using imageName")
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
