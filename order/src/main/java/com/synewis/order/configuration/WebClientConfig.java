package com.synewis.order.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient productClient(){
        return webClientBuilder().baseUrl("http://apiGateway/api/v1").build();
    }

    @Bean
    public WebClient inventoryClient(){
        return webClientBuilder().baseUrl("http://apiGateway/api/v1").build();
    }
}
