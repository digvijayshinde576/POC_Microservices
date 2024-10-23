package com.microservices.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@Component
public class filter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("Request Filtered.......!!!!!!!");

        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        Set<String> keySet = headers.keySet();

        if (!keySet.contains("secret")){
            throw new RuntimeException("Invalid Request......");
        }

        List<String> list = headers.get("secret");

        if (!list.get(0).equals("digvijay@123")){
            throw  new RuntimeException("invalid Request......");
        }


        return chain.filter(exchange);
    }
}
