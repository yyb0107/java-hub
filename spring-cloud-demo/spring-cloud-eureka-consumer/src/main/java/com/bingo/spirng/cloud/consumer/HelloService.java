package com.bingo.spirng.cloud.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * @author Bingo
 * @title: HelloService
 * @projectName java-hub
 * @description: TODO
 * @date 2020/1/18  13:40
 */
@Component
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    private static final String PROVIDE_URL_PREFIX ="http://service-provider-1/hello";

    public String hello(String name){
        ResponseEntity response = restTemplate.getForEntity(PROVIDE_URL_PREFIX+"/"+name,String.class);
        String resultValue = response.getBody().toString();
        return resultValue;
    }
}
