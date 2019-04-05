package com.bingo.java.pattern.proxy.staticproxy;

import com.bingo.java.pattern.proxy.pojo.Person;

/**
 * @author Bingo
 * @title: StaticProxyTest
 * @projectName java-hub
 * @description: 静态代理没有任何限制，不需要代理对象月被代理对象实现相同的接口,被代理对象也不一定要实现接口
 * @date 2019/3/28  23:07
 */
public class StaticProxyTest {
    public static void main(String[] args) {
        Person person = new Person();
        JobHunter jobHunter = new JobHunter(person);
        jobHunter.findJob();

    }
}
