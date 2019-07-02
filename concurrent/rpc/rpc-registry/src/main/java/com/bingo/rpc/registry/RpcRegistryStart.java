package com.bingo.rpc.registry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.bingo.rpc.registry")
public class RpcRegistryStart {

    @Bean(name="RPCServer")
    RpcRegistry server(){
        return new RpcRegistry(8080);
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RpcRegistryStart.class);
        ((AnnotationConfigApplicationContext) context).start();
//        RPCServer rpcServer = context.getBean(RPCServer.class);
    }
}