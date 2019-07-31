package com.bingo.rpc.client;

import com.bingo.rpc.api.IUserService;

import java.util.concurrent.TimeUnit;

/**
 * @author Bingo
 * @title: com.bingo.rpc.client.RPCClient
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/29  9:55
 */
public class RPCClient {
    public static void main(String[] args) throws InterruptedException {
        String host = "127.0.0.1";
        int port = 8080;

//       IUserService userService = RPCProxy.proxy(IUserService.class,host,port);


        for (int i = 0; i < 10; i++) {
            IUserService userService = RPCProxy.proxy(IUserService.class);
            System.out.println(userService.sayHello("hello"));
            TimeUnit.SECONDS.sleep(3);
        }
    }

}
