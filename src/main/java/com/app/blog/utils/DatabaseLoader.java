package com.app.blog.utils;

import com.app.blog.config.AppConstants;
import com.app.blog.entities.Role;
import com.app.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.passwordEncoder.encode("abcd"));

        try {
            Role admin = new Role();
            admin.setId(AppConstants.ADMIN_USER);
            admin.setName("ROLE_ADMIN");

            Role normal = new Role();
            normal.setId(AppConstants.NORMAL_USER);
            normal.setName("ROLE_NORMAL");

            List<Role> roles = List.of(admin,normal);
            List<Role> result = this.roleRepo.saveAll(roles);
            result.forEach(r -> {
                System.out.println(r.getName());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
