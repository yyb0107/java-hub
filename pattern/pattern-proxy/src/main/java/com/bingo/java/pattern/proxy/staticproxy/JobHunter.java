package com.bingo.java.pattern.proxy.staticproxy;

import com.bingo.java.pattern.proxy.pojo.Person;

/**
 * @author Bingo
 * @title: JobHunter
 * @projectName java-hub
 * @description: TODO
 */
public class JobHunter {
    Person person;

    public JobHunter(Person person) {
        this.person = person;
    }

    public void findJob() {
        before();
        person.findJob("static");
        after();
    }

    private void after() {
        System.out.println("找到合适的工作了。");
    }

    private void before() {
        System.out.println("根据需求搜集信息……");
    }
    
    
}
