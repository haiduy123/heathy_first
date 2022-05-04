package com.example.healthy_first_ver1.service;

import com.example.healthy_first_ver1.repository.result.UserResult;

public interface UserService {
    UserResult getUserByLogin(String username, String password);
}
