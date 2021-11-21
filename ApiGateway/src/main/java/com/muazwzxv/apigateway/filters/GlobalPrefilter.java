package com.muazwzxv.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class GlobalPrefilter implements GlobalFilter {

    final Logger logger = LoggerFactory.getLogger(GlobalPrefilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // Logic goes here
        logger.info("Global pre filter is executed......");

        String path = exchange.getRequest().getPath().toString();
        logger.info("Request path : " + path);

        HttpHeaders header = exchange.getRequest().getHeaders();

        Set<String> headerNames = header.keySet();

        headerNames.forEach(names -> {
            logger.info(names + " : " + header.getFirst(names));
        });

        // move to the next chain
        return chain.filter(exchange);
    }
}
