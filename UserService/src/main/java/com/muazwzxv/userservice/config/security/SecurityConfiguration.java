package com.muazwzxv.userservice.config.security;

import com.muazwzxv.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration @EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final Environment env;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    @Autowired
    public SecurityConfiguration(Environment env,  BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.env = env;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We disable csrf because we want to use jwt
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress(env.getProperty("gateway.ip"))
                .and()
                .addFilter(getAuthenticationFilter());
        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
        AuthenticationFilter authFilter = new AuthenticationFilter();
        authFilter.setAuthenticationManager(authenticationManager());
        return authFilter;
    }
}
