package com.bingo.java.pattern.proxy.bingoproxy;

import com.bingo.java.pattern.proxy.pojo.IPerson;
import com.bingo.java.pattern.proxy.pojo.Person;

/**
 * @author Bingo
 * @title: BGProxyTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/5  13:54
 */
public class BGProxyTest {
    public static void main(String[] args) {
        IPerson person = new Person();
        BGJobHunter hunter = new BGJobHunter();
        IPerson personProxy = (IPerson)hunter.proxy(person);
        personProxy.findJob("bingo hahahaha");
    }

}
