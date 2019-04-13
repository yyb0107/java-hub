package com.bingo.java.pattern.adapter.third;

import com.bingo.java.pattern.adapter.UserMsg;

/**
 * @author Bingo
 * @title: SinaLoginAdapter
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  15:29
 */
public class SinaLoginAdapter implements ThirdLoginAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof SinaLoginAdapter;
    }

    @Override
    public UserMsg login(String code) {
        return new UserMsg(200, "login by Sina", null);
    }
}
