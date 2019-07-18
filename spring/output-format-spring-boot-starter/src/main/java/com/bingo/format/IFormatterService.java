package com.bingo.format;


public interface IFormatterService {

    <T> String output(T t) throws Exception;
}
