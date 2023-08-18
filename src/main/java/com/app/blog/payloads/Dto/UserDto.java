package com.app.blog.payloads.Dto;

import com.app.blog.payloads.Dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private int id;
    @Size(min = 4,message = "username should be more than length of 4")
    private String name;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,message = "Invalid Email Address")
    private String email;
    @NotEmpty(message = "password shouldn't be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotEmpty(message = "about shouldn't be empty")
    private String about;
    private Set<RoleDto> roles = new HashSet<>();
}
