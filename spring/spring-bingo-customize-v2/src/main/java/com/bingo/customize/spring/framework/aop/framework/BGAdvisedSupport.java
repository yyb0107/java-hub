package com.bingo.customize.spring.framework.aop.framework;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bingo
 * @title: BGAdvisedSupport
 * @projectName java-hub
 */
@Getter
@Setter
public class BGAdvisedSupport {

    public Class<?> targetClass;

    public Object target;

    public BGAdvisedConfig config;


    public boolean pointCutMatch() {
        return false;
    }

    public BGAopProxy getProxy() {
        return null;
    }
}
