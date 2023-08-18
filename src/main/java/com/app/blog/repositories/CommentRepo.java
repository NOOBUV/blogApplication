package com.app.blog.repositories;

import com.app.blog.entities.Comment;
import com.app.blog.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
    Page<Comment> findByPost(Post post, Pageable p);
}
