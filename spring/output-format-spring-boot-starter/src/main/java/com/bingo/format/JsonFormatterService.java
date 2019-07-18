package com.bingo.format;

import com.alibaba.fastjson.JSON;

public class JsonFormatterService implements IFormatterService {
    @Override
    public <T> String output(T t) {
        return JSON.toJSON(t)+"";
    }
}
