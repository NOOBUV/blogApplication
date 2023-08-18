package com.app.blog.controllers;

import com.app.blog.payloads.ApiResponse;
import com.app.blog.payloads.Dto.CommentDto;
import com.app.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;
//    create
    @PostMapping("/post/{postId}/comments/user/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId, @PathVariable Integer userId) {
        CommentDto commentDto = this.commentService.createComment(comment,userId,postId);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }
//    getAllByPost
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Integer postId,
                                                              @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize) {
        List<CommentDto> comments = this.commentService.getCommentsByPost(postId,pageNumber,pageSize);
        return ResponseEntity.ok(comments);
    }
//    delete
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse("comment deleted successfully",true));
    }
//    update by user
//    to be made
}
