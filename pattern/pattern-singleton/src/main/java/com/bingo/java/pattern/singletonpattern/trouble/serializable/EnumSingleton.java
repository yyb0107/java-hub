package com.bingo.java.pattern.singletonpattern.trouble.serializable;

/**
 * @author Bingo
 * @title: EnumSingleton
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/23  21:39
 */
public enum EnumSingleton  {
    INSTANCE;

    private Object obj ;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public static EnumSingleton getInstance(){
        return INSTANCE;
    }

}
