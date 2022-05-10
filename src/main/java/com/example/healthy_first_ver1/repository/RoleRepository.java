package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
