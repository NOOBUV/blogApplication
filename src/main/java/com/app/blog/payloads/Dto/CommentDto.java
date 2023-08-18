package com.app.blog.payloads.Dto;

import com.app.blog.payloads.Dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter @Setter @NoArgsConstructor
public class CommentDto {
    private UserDto user;
    private Date date;
    private String content;
}
