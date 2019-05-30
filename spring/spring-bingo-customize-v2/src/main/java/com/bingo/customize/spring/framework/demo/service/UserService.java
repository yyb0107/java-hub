package com.bingo.customize.spring.framework.demo.service;

import com.bingo.customize.spring.framework.stereotype.BGService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bingo
 * @title: UserService
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/16  23:57
 */
@BGService
@Slf4j
public class UserService implements IUserService {
    @Override
    public User getUser(String id) {
        log.info("getUser(String id");
        return null;
    }
}
