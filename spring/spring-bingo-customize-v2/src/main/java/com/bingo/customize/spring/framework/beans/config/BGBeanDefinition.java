package com.bingo.customize.spring.framework.beans.config;

import lombok.Data;

/**
 * @author Bingo
 * @title: BGBeanDefinition
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/27  0:27
 */
@Data
public class BGBeanDefinition {

    private String beanClassName;
    private String factoryBeanName;
    private boolean isInitLazy;

}
