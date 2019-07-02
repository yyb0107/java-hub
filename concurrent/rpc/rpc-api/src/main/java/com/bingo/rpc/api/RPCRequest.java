package com.bingo.rpc.api;

import java.io.Serializable;

/**
 * @author Bingo
 * @title: RPCRequest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/27  23:10
 */
public class RPCRequest implements Serializable {

    private TYPE type;
    private String className;
    private String methodName;
    private Object[] args;
    private Class<?>[] parameterTypes;

    public RPCRequest(String className, String methodName, Object[] args, Class<?>[] parameterTypes) {
        this(TYPE.REQUEST,className,methodName,args,parameterTypes);
    }
    public RPCRequest(TYPE type,String className, String methodName, Object[] args, Class<?>[] parameterTypes) {
        this.type = type;
        this.className = className;
        this.methodName = methodName;
        this.args = args;
        this.parameterTypes = parameterTypes;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public static enum TYPE{
        REQUEST,
        REGISTRY
    }
}
