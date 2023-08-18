package com.app.blog.services.impl;

import com.app.blog.entities.Comment;
import com.app.blog.entities.Post;
import com.app.blog.entities.User;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.Dto.CommentDto;
import com.app.blog.repositories.CommentRepo;
import com.app.blog.repositories.PostRepo;
import com.app.blog.repositories.UserRepo;
import com.app.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User"," id",userId));
        Post post = this.postRepo.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post"," id",postId));
        Comment comment = this.modelMapper.map(commentDto,Comment.class);
        comment.setUser(user);
        comment.setPost(post);
        comment.setDate(new Date());
        Comment saveComment = this.commentRepo.save(comment);
        return this.modelMapper.map(saveComment,CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByPost(Integer postId,Integer pageNumber,Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber,pageSize);
        Post post = this.postRepo.findById(postId).
                orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        Page<Comment> pageComment = this.commentRepo.findByPost(post,p);
        List<Comment> commentList = pageComment.getContent();
        List<CommentDto> commentDtos = commentList.stream().
                map(comment -> this.modelMapper.map(comment,CommentDto.class)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto editComment(CommentDto commentDto, Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).
                orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));
        comment.setContent(commentDto.getContent());
        Comment saveComment = this.commentRepo.save(comment);
        return this.modelMapper.map(saveComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        this.commentRepo.deleteById(commentId);
    }
}
