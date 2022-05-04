package com.example.healthy_first_ver1.api.controller;

import com.example.healthy_first_ver1.api.response.ApiResponse;
import com.example.healthy_first_ver1.repository.result.UserResult;
import com.example.healthy_first_ver1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{username}/{password}")
    public ResponseEntity<ApiResponse> getResById(@PathVariable("username") String _username, @PathVariable("password") String _password) {
        UserResult user = userService.getUserByLogin(_username, _password);
        ApiResponse response = ApiResponse.success(user, HttpStatus.OK.value(), "ha");
        return ResponseEntity.ok(response);
    }
}
