package com.bingo.java.pattern.proxy.cglibproxy;

import com.bingo.java.pattern.proxy.pojo.IPerson;
import com.bingo.java.pattern.proxy.pojo.Person;
import net.sf.cglib.core.DebuggingClassWriter;

/**
 * @author Bingo
 * @title: CGLibProxyTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/5  23:08
 */
public class CGLibProxyTest {
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"D://cglib_proxy_classes");

        JobHunter hunter = new JobHunter();
        IPerson person = (IPerson) hunter.getInstance(Person.class);
        person.findJob("this is command!");
    }
}
