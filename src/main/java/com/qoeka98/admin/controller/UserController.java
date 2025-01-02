package com.qoeka98.admin.controller;

import com.qoeka98.admin.dto.RequsetUserDTO;
import com.qoeka98.admin.dto.UserLoginResponse;
import com.qoeka98.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/api/v1/admin/signup")
    public ResponseEntity<? extends Object> createdUser(@RequestBody RequsetUserDTO requsetUserDTO) {
        int result = userService.createdUser(requsetUserDTO);
        if (result == 1 || result == 2) {
            System.out.println("result 1,2");
            return ResponseEntity.status(401).build();
        }
        if (result == 3) {
            System.out.println("result 3");
            return ResponseEntity.status(400).build();
        }
        if (result == 0) {
            System.out.println("result 0");
            return ResponseEntity.status(200).body(requsetUserDTO);
        } else {
            return ResponseEntity.status(500).build();

        }

    }

    @PostMapping("/api/v1/admin/login")
    public ResponseEntity<UserLoginResponse> userLogin(@RequestBody RequsetUserDTO requsetUserDTO) {

        Object result = userService.Login(requsetUserDTO);
        if (result instanceof Integer) {
            return ResponseEntity.status(400).build();

        }return ResponseEntity.status(200).body(new UserLoginResponse((String) result));

    }






    }
