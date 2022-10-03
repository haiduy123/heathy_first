package com.example.healthy_first_ver1.service;

import com.example.healthy_first_ver1.api.form.UserForm;
import com.example.healthy_first_ver1.entity.Role;
import com.example.healthy_first_ver1.entity.User;
import com.example.healthy_first_ver1.repository.result.UserResult;

public interface UserService {
    UserResult getUserByLogin(String username, String password);
    void addRoleToUser(String username, String roleName);
    User saveUser(User user);
    Role saveRole(Role role);
    User getUserByName(String username);
    User addNewCandidate(User user);
    User addNewCompany(User user);
}
