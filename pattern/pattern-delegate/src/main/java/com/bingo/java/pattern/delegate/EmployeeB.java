package com.bingo.java.pattern.delegate;

/**
 * @author Bingo
 * @title: EmployeeB
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/6  12:20
 */
public class EmployeeB implements Employee {
    @Override
    public void doWork() {
        System.out.println("我是员工B 我负责前端");
    }
}
