package com.bingo.java.pattern.singletonpattern;

/**
 * @author Bingo
 * @title: EnumSingleton
 * @projectName java-hub
 * @date 2019/3/23
 */
public enum EnumSingleton {
    INSTANCE;

    private Object obj ;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        if(this.obj==null){
            this.obj = obj;
        }else{
            throw new RuntimeException("Cannot call this method when obj was existed");
        }
    }
    public static EnumSingleton getInstance(){
        return INSTANCE;
    }
}
