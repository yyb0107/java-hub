package com.bingo.spirng.cloud.consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Bingo
 * @title: EurekaConsumerApp
 * @projectName java-hub
 * @description: TODO
 * @date 2020/1/18  13:35
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaConsumerApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(EurekaConsumerApp.class).run(args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
