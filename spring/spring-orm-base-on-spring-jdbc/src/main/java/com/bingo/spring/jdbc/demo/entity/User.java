package com.bingo.spring.jdbc.demo.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author Bingo
 * @title: User
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/1  17:40
 */
@Getter
@Setter
public class User implements Serializable {
//    @Column(name="userid")
    private int userId;
    private String username;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + username + '\'' +
                '}';
    }
}
