package com.app.blog.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    private User user;
}
