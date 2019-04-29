package com.bingo.customize.spring.framework.beans.support;

import com.bingo.customize.spring.framework.beans.BGBeanFactory;
import com.bingo.customize.spring.framework.beans.config.BGBeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bingo
 * @title: BGDefaultListableBeanFactory
 * @projectName java-hub
 */
public abstract class BGDefaultListableBeanFactory implements BGBeanFactory {
    /**
     * Map of bean definition objects, keyed by bean name.
     */
    private final Map<String, BGBeanDefinition> beanDefinitionMap=new ConcurrentHashMap<>();

    public Map<String, BGBeanDefinition> getBeanDfinitionMap() {
        return beanDefinitionMap;
    }

}
