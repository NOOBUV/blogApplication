package com.app.blog.repositories;

import com.app.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
