package com.muazwzxv.apigateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthHeaderFilter extends AbstractGatewayFilterFactory<AuthHeaderFilter.Config> {

    private final Environment env;

    @Autowired
    public AuthHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {
        // Configuration properties goes here
    }


    @Override
    public GatewayFilter apply(Config config) {
        // We will approach this code using lambdas and reactive code
        return ((exchange, chain) -> {

            ServerHttpRequest req = exchange.getRequest();

            if (!req.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);

            String[] jwt = (req.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0)).split(" ", 2);

            if (!isJWTValid(jwt[1])) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange ex, String err, HttpStatus status) {
        ServerHttpResponse res = ex.getResponse();
        res.setStatusCode(status);

        return res.setComplete();
    }

    private boolean isJWTValid(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(env.getProperty("jwt.secret")))
                    .build();
            verifier.verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
