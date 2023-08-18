package com.app.blog.services.impl;

import com.app.blog.config.AppConstants;
import com.app.blog.entities.Role;
import com.app.blog.entities.User;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.payloads.Dto.UserDto;
import com.app.blog.repositories.RoleRepo;
import com.app.blog.repositories.UserRepo;
import com.app.blog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }
    public UserDto registerUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto,User.class);
//        encode the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
//        roles
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);

        return this.modelMapper.map(newUser,UserDto.class);
//
    }
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User"," id ",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User"," id ",userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User"," id ",userId));
        user.getRoles().clear();
        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto,User.class);
//        user.setName(userDto.getName());
//        user.setId(userDto.getId());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }

}
