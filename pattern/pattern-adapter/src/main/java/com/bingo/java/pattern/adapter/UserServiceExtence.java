package com.bingo.java.pattern.adapter;

import com.bingo.java.pattern.adapter.UserMsg;
import com.bingo.java.pattern.adapter.third.*;

/**
 * @author Bingo
 * @title: UserServiceExtence
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/13  15:33
 */
public class UserServiceExtence extends UserService implements ThirdLoginService {
    @Override
    public UserMsg loginEcode(String code) throws InstantiationException, IllegalAccessException {
        return login(code, ECodeLoginAdapter.class);
    }

    @Override
    public UserMsg LoginSina(String sina) throws InstantiationException, IllegalAccessException {
        return login(sina, SinaLoginAdapter.class);
    }

    @Override
    public UserMsg LoginWeChat(String wechat) throws InstantiationException, IllegalAccessException {
        return login(wechat, WechatLoginAdapter.class);
    }

    private UserMsg login(String code, Class<?> adapter) throws IllegalAccessException, InstantiationException {
        Object obj = adapter.newInstance();
        if (obj instanceof ThirdLoginAdapter) {
            ((ThirdLoginAdapter) obj).support(obj);
            return ((ThirdLoginAdapter) obj).login(code);
        }

        return null;
    }
}
