package com.app.blog.services;

import com.app.blog.payloads.Dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto updateUser(UserDto user, Integer userId);
    UserDto getUserById(Integer userId);
    UserDto registerUser(UserDto user);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
