package com.bingo.spirng.cloud.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Bingo
 * @title: EurekaSreverApp
 * @projectName java-hub
 * @description: TODO
 * @date 2020/1/11  16:15
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(EurekaServerApp.class).web(WebApplicationType.SERVLET).run(args);
    }
}
