package com.bingo.java.pattern.singletonpattern;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bingo
 * @title: ContainerSingleton
 * @projectName java-hub
 */
public class ContainerSingleton {
    private static final Map<String,Object> container = new HashMap<String,Object>();

    /**
     * 如果需要线程安全，记得使用synchronized关键字
     * @param className
     * @return
     */
    public static Object getInstance(String className){
        try {
            if(!container.containsKey(className)){
                Class clazz = Class.forName(className);
                Object obj = clazz.newInstance();
                container.put(className,obj);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return container.get(className);
    }

}
