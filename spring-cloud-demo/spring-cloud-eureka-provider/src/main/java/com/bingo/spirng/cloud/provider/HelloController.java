package com.bingo.spirng.cloud.provider;

/**
 * @author Bingo
 * @title: HelloController
 * @projectName java-hub
 * @description: TODO
 * @date 2020/1/18  10:51
 */

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name){
        return name +"\tHello World!";
    }

}
