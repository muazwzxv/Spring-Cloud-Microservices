package com.muazwzxv.userservice.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muazwzxv.userservice.models.dto.LoginRequestModel;
import com.muazwzxv.userservice.models.Users;
import com.muazwzxv.userservice.services.UserService;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;
    private final Environment env;

    public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager auth) {
        super.setAuthenticationManager(auth);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestModel login = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);

            return getAuthenticationManager()
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            login.getEmail(),
                            login.getPassword(),
                            new ArrayList<>()
                    ));
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This method is automatically by Spring after a successful authentication process
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {

        String email = ((User) auth.getPrincipal()).getUsername();

        Users user = userService.getUserByEmail(email);
        String token = JWT.create().withSubject(user.getUserId())
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("jwt.expiresAt"))))
                .sign(Algorithm.HMAC256(env.getProperty("jwt.secret")));

        response.addHeader("token", token);
        response.addHeader("userId", user.getUserId());
    }
}
