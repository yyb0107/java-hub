package com.bingo.java.pattern.adapter.third;

import com.bingo.java.pattern.adapter.UserMsg;

public interface ThirdLoginAdapter {

    boolean support(Object adapter);

    UserMsg login(String code);
}
