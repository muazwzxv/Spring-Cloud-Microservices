package com.muazwzxv.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfig {

    Logger logger = LoggerFactory.getLogger(GlobalPostFilter.class);


    /***
     *
     * The @order(i) indicates the order of the filters
     * Lower i means higher priority for pre filter and lower priority for post filter
     */
    @Bean
    @Order(1)
    public GlobalFilter nextPreFilter() {
        return ((exchange, chain) -> {
            // Pre filter
            logger.info("Next Global pre filter is executed .......");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Post filter
                logger.info("Next Global post filter is executed .......");
            }));
        });
    }

}
