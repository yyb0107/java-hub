package com.bingo.spirng.cloud.provider;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Bingo
 * @title: ServiceProviderApp
 * @projectName java-hub
 * @description: TODO
 * @date 2020/1/18  11:01
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceProviderApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(ServiceProviderApp.class).web(WebApplicationType.SERVLET).run(args);
    }
}
