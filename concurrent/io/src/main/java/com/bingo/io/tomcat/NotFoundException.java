package com.bingo.io.tomcat;

/**
 * @author Bingo
 * @title: NotFoundException
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/30  18:00
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
