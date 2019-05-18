package com.bingo.customize.spring.framework.mvc.servlet;

import com.bingo.customize.spring.framework.stereotype.BGRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

/**
 * @author Bingo
 * @title: BGHandlerAdapter
 * @projectName java-hub
 */
public class BGHandlerAdapter {
    boolean supports(Object handler) {
        return handler instanceof BGHandlerMapping;
    }

    BGModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof BGHandlerMapping) {
            Method method = ((BGHandlerMapping) handler).getMethod();
            Parameter[] parameters = method.getParameters();
            //方法的实参列表
            ArrayList<Object> paramList = new ArrayList<>();
            for (Parameter parameter : parameters) {
                if (parameter.getType().getName().endsWith("Request")) {
                    paramList.add(request);
                    continue;
                }
                if (parameter.getType().getName().endsWith("Response")) {
                    paramList.add(response);
                    continue;
                }
                if (parameter.isAnnotationPresent(BGRequestParam.class)) {
                    String paramName = ((BGRequestParam) parameter.getAnnotation(BGRequestParam.class)).value();
                    String paramValue = request.getParameter(paramName);
                    //新增参数的类型转换，将转换的逻辑从controller 转到这里来
                    Object paramValueAfterConvert = convert(paramValue, parameter.getType());
                    paramList.add(paramValueAfterConvert);
                }

            }
            Object obj = method.invoke(((BGHandlerMapping) handler).getDefaultHandler(), paramList.toArray());
            BGModelAndView modelAndView = null;
            if (obj instanceof String) {
                modelAndView = new BGModelAndView();
                modelAndView.setViewName(obj.toString());
            } else if (obj instanceof BGModelAndView) {
                modelAndView = (BGModelAndView) obj;
            }
            return modelAndView;
        }
        return null;
    }

    //将reqeust获取的参数按照对应方法参数类型进行转化
    private Object convert(String paramValue, Class<?> type) {
//首先看目标类型是否为String
        if (type == String.class) return paramValue;
        return null;
    }
}
