package com.app.blog.services;

import com.app.blog.payloads.Dto.CommentDto;

import java.util.List;

public interface CommentService {
//    create
    CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId);
//    get
    List<CommentDto> getCommentsByPost(Integer postId,Integer pageNumber,Integer pageSize);
//    update
    CommentDto editComment(CommentDto commentDto, Integer commentId);
//    delete
    void deleteComment(Integer commentId);
}
