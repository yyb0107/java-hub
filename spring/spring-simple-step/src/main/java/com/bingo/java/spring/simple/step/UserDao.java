package com.bingo.java.spring.simple.step;

import org.springframework.stereotype.Component;

/**
 * @author Bingo
 * @title: UserDao
 * @projectName java-hub
 */
@Component
public class UserDao {
    User login(User user){
        if(user.getPassword().equals("password") && user.getUname().equals("uname")){
            return user;
        }
        return null;

    }
}
