package com.bingo.java.spring.customize.mvc.converter;

import java.text.SimpleDateFormat;

public interface Converter<S, T> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    T convert(S s, Class<T> t);
}
