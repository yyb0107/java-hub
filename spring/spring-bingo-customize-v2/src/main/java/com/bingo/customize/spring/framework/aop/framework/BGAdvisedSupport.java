package com.bingo.customize.spring.framework.aop.framework;

import com.bingo.customize.spring.framework.aop.framework.aspectj.BGAfterThrowingAdviceInterceptor;
import com.bingo.customize.spring.framework.aop.framework.aspectj.BGMethodBeforeAdviceInterceptor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.*;
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

    Map<String, List<Object>> methodCache = new HashMap<>();

    private Map<String, Method> aspectMethods = new HashMap<String, Method>();

    private Map<String, Class<?>> aspectClasses = new HashMap<String, Class<?>>();


    public boolean pointCutMatch() throws ClassNotFoundException {
        if (aspectMethods.isEmpty()) {
            Class clazz = Class.forName(config.getAspectClass());
            aspectClasses.computeIfAbsent(config.getAspectClass(), l->clazz);
            Method[] mm = clazz.getMethods();
            for (Method m : mm) {
                aspectMethods.put(m.getName(), m);
            }

        }
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

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws IllegalAccessException, InstantiationException {
        String cacheKey = method.getName();
        List<Object> cached = this.methodCache.get(cacheKey);
        if (cached == null) {
//            cached =
            boolean needInterceptor = false;
            for (Method m : needAopMethods) {
                if (m.getName().equals(cacheKey)) {
                    needInterceptor = true;
                    break;
                }
            }
            if (needInterceptor) {
                //执行器链
                List<Object> advices = new LinkedList<Object>();
                //开始为每一个方法加入拦截的方法，根据方法的签名
                if (!(this.config.getAspectBefore().equals("") || this.config.getAspectBefore() != null)) {
                    advices.add(new BGMethodBeforeAdviceInterceptor(this.aspectMethods.get(this.config.getAspectBefore()),aspectClasses.get(this.config.getAspectClass()).newInstance()));
                }
                if (!(this.config.getAspectAfter().equals("") || this.config.getAspectAfter() != null)) {
                    advices.add(new BGMethodBeforeAdviceInterceptor(this.aspectMethods.get(this.config.getAspectAfter()),aspectClasses.get(this.config.getAspectClass()).newInstance()));
                }
                if (!(this.config.getAspectAfterThrow().equals("") || this.config.getAspectAfterThrow() != null)) {
                    advices.add(new BGAfterThrowingAdviceInterceptor(this.aspectMethods.get(this.config.getAspectAfterThrow()),aspectClasses.get(this.config.getAspectClass()).newInstance()));
                }
            }
            this.methodCache.put(cacheKey, cached);
        }
        return cached;
    }
}
