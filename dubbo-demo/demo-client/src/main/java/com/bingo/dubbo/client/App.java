package com.bingo.dubbo.client;

import com.bingo.service.api.IHelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Bingo
 * @title: App
 * @projectName java-hub
 * @description: TODO
 * @date 2019/8/10  11:45
 */
public class App {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        context.start();
        IHelloService service = (IHelloService)context.getBean("helloService");
        String out = service.sayHello("Bingo");
        System.out.println(out);
        System.in.read();
    }
}
