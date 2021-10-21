package com.muazwzxv.userservice.controllers;

import com.muazwzxv.userservice.models.dto.CreateUserResponseModel;
import com.muazwzxv.userservice.models.Users;
import com.muazwzxv.userservice.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final Environment env;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UsersController(UserService userService, Environment env, ModelMapper modelMapper) {
        this.userService = userService;
        this.env = env;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseModel> create(@RequestBody Users user) {
        Users created = this.userService.createUser(user);

        CreateUserResponseModel toReturn =  modelMapper.map(created, CreateUserResponseModel.class);
        System.out.println(created.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(toReturn);
    }

    @GetMapping("/status/check")
    public String status() {
        return "Status check from User Service on port " + env.getProperty("local.server.port");
    }
}
