package com.bingo.customize.spring.framework.aop.framework;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    List<Method> needAopMethods = new ArrayList<>();


    public boolean pointCutMatch() {
        String regex = config.getPointCut();
        String className = targetClass.getClass().getName();
        Method[] methods = targetClass.getMethods();
        Pattern pointCutPattern = Pattern.compile(regex);
        boolean isMatches = false;
        Matcher matcher = null;
        for(Method method:methods){
            matcher = pointCutPattern.matcher(method.toString());
            if(matcher.find()){
                needAopMethods.add(method);
                isMatches = true;
            }
        }


        return isMatches;
    }

    public BGAopProxy getProxy() {
        if(targetClass.getClass().getInterfaces().length>0){
            try {
                return new BGJdkDynamicAopProxy(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            throw new UnsupportedOperationException();
        }
        return null;
    }
}
