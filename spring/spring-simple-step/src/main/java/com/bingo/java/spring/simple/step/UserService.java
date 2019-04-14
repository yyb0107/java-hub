package com.bingo.java.spring.simple.step;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bingo
 * @title: UserService
 * @projectName java-hub
 */
@Component
public class UserService {
    @Autowired
    UserDao userDao;

    public User login(User user) {
        User rs = userDao.login(user);
        if(rs!=null){
            System.out.println("用户登陆成功");
        }else{
            System.out.println("用户登陆失败");
        }
        return rs;
    }
}
