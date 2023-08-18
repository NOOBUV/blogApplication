package com.app.blog.controllers;

import com.app.blog.payloads.ApiResponse;
import com.app.blog.payloads.Dto.UserDto;
import com.app.blog.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

//  Post-create user
//    @PostMapping("/")
//    public ResponseEntity<UserDto> createUser(@Validated @RequestBody UserDto userDto) {
//        UserDto createUserDto = this.userService.createUser((userDto));
//        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
//    }
//  Put-update user
    @Operation(summary = "Update user data", description = "update user data (only user himself should be able to update)")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('NORMAL') and #userId == authentication.principal.id")
    public ResponseEntity<UserDto> updateUser(@Validated @RequestBody UserDto userDto,@PathVariable("userId") Integer userId) {
        UserDto updatedUser = this.userService.updateUser(userDto,userId);
        return ResponseEntity.ok(updatedUser);
    }
//    Delete user
//    only admin should be able to do that
    @Operation(summary = "Delete user", description = "Delete user (admin only)")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
        this.userService.deleteUser(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true), HttpStatus.OK);
    }
//    Get all users
    @Operation(summary = "Get all users", description = "Get all users")
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
//    Get particular user
    @Operation(summary = "Get user", description = "get user with userid")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer uid) {
        return ResponseEntity.ok(this.userService.getUserById(uid));
    }
}
