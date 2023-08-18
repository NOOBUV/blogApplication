package com.app.blog.security;

import com.app.blog.entities.User;
import com.app.blog.exceptions.ResourceNotFoundException;
import com.app.blog.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
//    this class will provide passcodes relating users
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User"," email : "+username, 0));
        System.out.println(username + user.getUsername() + user.getPassword());
        return user;
    }

}
