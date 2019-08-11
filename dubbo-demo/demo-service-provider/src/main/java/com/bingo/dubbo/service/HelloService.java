package com.bingo.dubbo.service;

import com.bingo.service.api.IHelloService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author Bingo
 * @title: HelloService
 * @projectName java-hub
 * @description: TODO
 * @date 2019/8/10  11:34
 */
@Path("/hello")
public class HelloService implements IHelloService {
    @Override
    @GET
    @Path("/test/{text}")
    public String sayHello(@PathParam("text") String text) {
        return "Hello "+text;
    }
}
