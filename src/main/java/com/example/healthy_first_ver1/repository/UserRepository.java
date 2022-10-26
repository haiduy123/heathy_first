package com.example.healthy_first_ver1.repository;

import com.example.healthy_first_ver1.entity.User;
import com.example.healthy_first_ver1.repository.result.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select u.username, u.position, u.location\n" +
            "from ooad.users u\n" +
            "where u.username = :username \n" +
            "and u.password = :password", nativeQuery = true)
    UserResult getUserByLogin(String username, String password);

    User findByUsername(String username);

    @Query(value = "select u.username from ooad.users u", nativeQuery = true)
    List<String> getListUsername();
}
