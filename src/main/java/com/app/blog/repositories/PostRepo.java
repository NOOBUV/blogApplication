package com.app.blog.repositories;

import com.app.blog.entities.Category;
import com.app.blog.entities.Post;
import com.app.blog.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer > {
    Page<Post> findByUser(User user, Pageable p);
    Page<Post> findByCategory(Category category, Pageable p);
    @Query("select p from Post p where p.title like :key")
    Page<Post> searchByTitle(@Param("key") String title,Pageable p);
}
