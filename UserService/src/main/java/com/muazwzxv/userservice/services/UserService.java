package com.muazwzxv.userservice.services;

import com.muazwzxv.userservice.models.Users;
import com.muazwzxv.userservice.repositories.UserRepository;
import com.muazwzxv.userservice.services.Interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcrypt;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bcrypt) {
        this.userRepository = userRepository;
        this.bcrypt = bcrypt;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if(user == null) throw new UsernameNotFoundException(email);

        return new User(user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public Users createUser(Users user)  {
        user.setPassword(bcrypt.encode(user.getPassword()));

        // Generate uuid for public userId
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public Users getUserByEmail(String email) throws UsernameNotFoundException{
        Users user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException(email);

        return user;
    }
}
