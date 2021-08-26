package com.bingo.cache.redis;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class UserInfo implements Serializable {
    private Integer id;
    private String name;
    private String sex;
    private Integer age;
}