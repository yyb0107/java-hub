package com.bingo.java.pattern.adapter.third;

import com.bingo.java.pattern.adapter.UserMsg;

/**
 * @author Bingo
 * @title: ECodeLoginAdapter
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  15:32
 */
public class ECodeLoginAdapter implements ThirdLoginAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof ECodeLoginAdapter;
    }

    @Override
    public UserMsg login(String code) {
        return new UserMsg(200,"login by ecode", null);
    }
}
