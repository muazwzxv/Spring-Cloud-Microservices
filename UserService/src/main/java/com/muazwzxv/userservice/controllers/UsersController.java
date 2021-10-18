package com.muazwzxv.userservice.controllers;

import com.muazwzxv.userservice.models.Users;
import com.muazwzxv.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final Environment env;
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService, Environment env) {
        this.userService = userService;
        this.env = env;
    }

    @PostMapping
    public String create(@RequestBody Users user) {
        this.userService.createUser(user);

        return "user is created";
    }

    @GetMapping("/status/check")
    public String status() {
        return "Status check from User Service on port " + env.getProperty("local.server.port");
    }
}
