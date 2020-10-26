package com.bingo.java.spring.customize.mvc.converter;

public class StringToDoubleConverter implements Converter<String, Double> {
    @Override
    public Double convert(String s, Class<Double> t) {
        return Double.parseDouble(s);
    }
}
