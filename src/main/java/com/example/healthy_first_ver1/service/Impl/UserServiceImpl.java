package com.example.healthy_first_ver1.service.Impl;

import com.example.healthy_first_ver1.entity.User;
import com.example.healthy_first_ver1.repository.UserRepository;
import com.example.healthy_first_ver1.repository.result.UserResult;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserResult getUserByLogin(String username, String password) {
        UserResult user = userRepository.getUserByLogin(username, password);
        return user;
    }
}
