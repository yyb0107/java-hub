package com.bingo.java.pattern.adapter.third;

import com.bingo.java.pattern.adapter.UserMsg;

public interface ThirdLoginService {
    UserMsg loginEcode(String code) throws InstantiationException, IllegalAccessException;

    UserMsg LoginSina(String sina) throws InstantiationException, IllegalAccessException;

    UserMsg LoginWeChat(String wechat) throws InstantiationException, IllegalAccessException;
}
