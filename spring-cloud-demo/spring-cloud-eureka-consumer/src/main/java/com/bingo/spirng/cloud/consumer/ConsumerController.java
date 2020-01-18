package com.bingo.spirng.cloud.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bingo
 * @title: ConsumerController
 * @projectName java-hub
 * @description: TODO
 * @date 2020/1/18  13:38
 */
@RestController
public class ConsumerController {
    @Autowired
    HelloService service;

    @RequestMapping("/say/{name}")
    public String sayHello(@PathVariable("name")String name) {
        return service.hello(name);
    }

}
