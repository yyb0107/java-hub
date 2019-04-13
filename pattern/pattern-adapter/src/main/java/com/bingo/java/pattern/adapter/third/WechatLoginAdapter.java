package com.bingo.java.pattern.adapter.third;

import com.bingo.java.pattern.adapter.UserMsg;

/**
 * @author Bingo
 * @title: WechatLoginAdapter
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  15:30
 */
public class WechatLoginAdapter implements ThirdLoginAdapter {
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof WechatLoginAdapter;
    }

    @Override
    public UserMsg login(String code) {
        return new UserMsg(200,"login by wechat ",null);
    }
}
