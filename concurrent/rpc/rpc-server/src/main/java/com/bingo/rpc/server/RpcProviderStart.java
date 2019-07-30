package com.bingo.rpc.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

/**
 * @author Bingo
 * @title: SpringStart
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  11:12
 */
@Configuration
@ComponentScan("com.bingo.rpc.server")
public class RpcProviderStart {
    @Bean
    RPCServer server(){
        return new RPCServer(8089);
    }

    @Primary
    @Bean
    RPCServer rpcServer2(){
        return new RPCServer(8090);
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(RpcProviderStart.class);
        ((AnnotationConfigApplicationContext) context).start();
//        RPCServer rpcServer = context.getBean(RPCServer.class);
    }
}
