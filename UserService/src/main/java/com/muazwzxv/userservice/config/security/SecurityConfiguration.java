package com.muazwzxv.userservice.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration @EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final Environment env;

    @Autowired
    public SecurityConfiguration(Environment env) {
        this.env = env;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We disable csrf because we want to use jwt
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"));
        http.headers().frameOptions().disable();
    }
}
