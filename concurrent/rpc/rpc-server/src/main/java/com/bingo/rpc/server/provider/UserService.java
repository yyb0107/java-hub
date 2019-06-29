package com.bingo.rpc.server.provider;

import com.bingo.rpc.api.IUserService;
import com.bingo.rpc.api.User;
import com.bingo.rpc.server.annotation.RPCService;
import org.springframework.stereotype.Component;

/**
 * @author Bingo
 * @title: UserService
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:26
 */
@RPCService("IUserService")
public class UserService implements IUserService {
    @Override
    public String sayHello(String text) {
        System.out.println("UserService sayHello :"+text);
        return "Thanks";
    }

    @Override
    public int saveUser(User user) {
        System.out.println("UserService saveUser :"+user);
        return 0;
    }
}
