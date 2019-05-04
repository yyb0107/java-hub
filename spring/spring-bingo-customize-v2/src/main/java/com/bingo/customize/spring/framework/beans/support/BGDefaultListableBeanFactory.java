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

//    private

    public Map<String, BGBeanDefinition> getBeanDfinitionMap() {
        return beanDefinitionMap;
    }

    protected BGBeanWrapper instantiateBean(final String beanName, final BGBeanDefinition bd) {
        String className = bd.getBeanClassName();
        BGBeanWrapper bgBeanWrapper = null;
        try {
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.newInstance();
            bgBeanWrapper = new BGBeanWrapper();
            bgBeanWrapper.setWrappedInstance(obj);
            return bgBeanWrapper;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void populateBean(String beanName, BGBeanDefinition mbd, BGBeanWrapper bw) {

    }

}
