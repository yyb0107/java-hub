package com.bingo.customize.spring.framework.beans.support;

import com.bingo.customize.spring.framework.beans.BGBeanFactory;
import com.bingo.customize.spring.framework.beans.config.BGBeanDefinition;
import com.bingo.customize.spring.framework.stereotype.BGAutowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

    /**
     * Cache of unfinished FactoryBean instances: FactoryBean name to BeanWrapper.
     */
    private final ConcurrentMap<String, BGBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    /**
     * Cache of singleton objects: bean name to bean instance.
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
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
            factoryBeanInstanceCache.putIfAbsent(beanName, bgBeanWrapper);
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
        Object obj = bw.getWrappedInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        String fieldTypeSimpleName = "";
        try {
            for (Field field : fields) {
                fieldTypeSimpleName = "";
                if (field.isAnnotationPresent(BGAutowired.class)) {
                    Method method = obj.getClass().getMethod("set" + firstCharacherUpper(field.getName()), field.getType());
                    //从IOC获取对应name的bean
                    fieldTypeSimpleName = field.getType().getSimpleName();
                    Object param = factoryBeanInstanceCache.get(firstCharacterLower(fieldTypeSimpleName));
                    method.setAccessible(true);
                    if (param != null) {
                        method.invoke(obj, param);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void addSingletonObjects(String beanName, Object singletonObject) {
        this.singletonObjects.put(beanName, singletonObject);
    }

    private String firstCharacterLower(String simpleName) {
        return simpleName.replace(simpleName.charAt(0), (char) (simpleName.charAt(0) + 32));
    }

    private String firstCharacherUpper(String simpleName) {
        return simpleName.replace(simpleName.charAt(0), (char) (simpleName.charAt(0) - 32));
    }

}
