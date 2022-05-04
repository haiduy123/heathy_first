package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.User;
import com.example.healthy_first_ver1.repository.result.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u.username, u.position, u.location\n" +
            "from web_project.users u\n" +
            "where u.username = :username \n" +
            "and u.password = :password", nativeQuery = true)
    UserResult getUserByLogin(String username, String password);
}
