package com.muazwzxv.userservice.controllers;

import com.muazwzxv.userservice.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String status() {
       return "Status check from User Service on port " + env.getProperty("local.server.port");
    }

    @PostMapping
    public String create(@RequestBody Users user) {
        return "create user method is called";
    }
}
