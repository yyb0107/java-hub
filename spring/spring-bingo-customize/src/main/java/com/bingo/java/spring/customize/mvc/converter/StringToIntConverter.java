package com.bingo.java.spring.customize.mvc.converter;

public class StringToIntConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String s, Class<Integer> t) {
        return Integer.parseInt(s);
    }
}
