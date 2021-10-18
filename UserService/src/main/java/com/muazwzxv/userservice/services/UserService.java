package com.muazwzxv.userservice.services;

import com.muazwzxv.userservice.models.Users;
import com.muazwzxv.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createUser(Users user)  {
        System.out.println(user.toString());
        return userRepository.save(user);
    }
}
