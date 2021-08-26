package com.bingo.cache.spingcafeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringCafeinApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringCafeinApplication.class,args);
    }
}
