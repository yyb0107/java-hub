package com.bingo.rpc.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bingo
 * @title: SpringStart
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  11:12
 */
@Configuration
@ComponentScan("com.bingo.rpc.server")
public class SpringStart {

    @Bean(name="RPCServer")
    RPCServer server(){
        return new RPCServer(8080);
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringStart.class);
        ((AnnotationConfigApplicationContext) context).start();
//        RPCServer rpcServer = context.getBean(RPCServer.class);
    }
}
