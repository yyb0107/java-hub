package com.bingo.customize.spring.framework.beans.support;

/**
 * @author Bingo
 * @title: BGBeanWrapper
 * @projectName java-hub
 * @description: TODO
 * @date 2019/5/4  23:27
 */
public class BGBeanWrapper {
    Object wrappedInstance;
    Class<?> wrapperClass;

    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    public Class<?> geteWrapperClass(){
        return this.wrapperClass;
    }

    public void setWrappedInstance(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
        this.wrapperClass = wrappedInstance.getClass();
    }
}
