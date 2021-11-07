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
    private final AuthenticationFilter authFilter;

    @Autowired
    public SecurityConfiguration(Environment env, AuthenticationFilter authFilter) {
        this.authFilter = authFilter;
        this.env = env;
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

    private AuthenticationFilter getAuthenticationFilter() throws Exception{
       this.authFilter.setAuthenticationManager(authenticationManager());
       return this.authFilter;
    }
}
