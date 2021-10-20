package com.muazwzxv.userservice.services;

import com.muazwzxv.userservice.models.Users;
import com.muazwzxv.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcrypt;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    public Users createUser(Users user)  {
        System.out.println(user.toString());
        return userRepository.save(user);
    }
}
