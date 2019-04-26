package com.bingo.customize.spring.framework.beans;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bingo
 * @title: BGDefaultListableBeanFactory
 * @projectName java-hub
 */
public abstract class BGDefaultListableBeanFactory{
/**
 * Map of bean definition objects, keyed by bean name.
 */
private final Map<String, BGBeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>();
}
