package com.bingo.java.pattern.proxy.jdkproxy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.bingo.java.pattern.proxy.pojo.IPerson;
import com.bingo.java.pattern.proxy.pojo.Person;
import sun.misc.ProxyGenerator;

/**
 * @author Bingo
 * @title: JdkProxyTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/3/28  23:12
 */
public class JdkProxyTest {

    public static void main(String[] args) {
        JobHunter jobHunter = new JobHunter();
        IPerson person = new Person();
        IPerson personProxy = jobHunter.getInstance(person);
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{IPerson.class});
        try(FileOutputStream fileOutputStream = new FileOutputStream("proxy.class");) {
            fileOutputStream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        personProxy.findJob("jdk");
//        personProxy.hahaha("11111");
        personProxy.toString();
    }

}
