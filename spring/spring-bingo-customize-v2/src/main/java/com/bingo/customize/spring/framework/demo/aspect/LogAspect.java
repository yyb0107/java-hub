package com.bingo.customize.spring.framework.demo.aspect;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bingo
 * @title: LogAspect
 * @projectName java-hub
 */
@Slf4j
public class LogAspect {

    public void before(){
        log.info("before");

    }

    public void after(){
        log.info("after");
    }

    public void afterThrowing(Exception e){
        log.info("afterThrowing");
    }

    public void around(){
        log.info("around"+System.currentTimeMillis());
    }


}
