package com.bingo.rpc.api;

/**
 * @author Bingo
 * @title: IUserService
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:21
 */
public interface IUserService {

    String sayHello(String text);

    int saveUser(User user);
}
