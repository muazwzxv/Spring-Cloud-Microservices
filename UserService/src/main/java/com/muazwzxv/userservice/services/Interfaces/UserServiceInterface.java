package com.muazwzxv.userservice.services.Interfaces;

import com.muazwzxv.userservice.models.Users;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserServiceInterface extends UserDetailsService {
    Users createUser(Users user);
    Users getUserByEmail(String email);
}
