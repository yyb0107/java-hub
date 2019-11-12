package com.bingo.springboot.autoconfigure;

import com.bingo.springboot.autoconfigure.entity.User;
import com.bingo.springboot.autoconfigure.format.FormatService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationStart {
    public static void main(String[] args) throws Exception {
        ApplicationContext application =  SpringApplication.run(ApplicationStart.class,args);
//        String[] names = application.getBeanDefinitionNames();
//        for(String name:names ){
//            System.out.println(name);
//        }
        FormatService service =  application.getBean(FormatService.class);
//        IFormatterService services = new XmlFormatterService();
        String out = service.format(new User("bingo","430321",18,"hunan"));
        System.out.println("结果是 ："+out);
    }
}