package com.bingo.java.pattern.delegate;

/**
 * @author Bingo
 * @title: EmployeeA
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/6  12:20
 */
public class EmployeeA implements Employee {
    @Override
    public void doWork() {
        System.out.println("我是员工A 我负责后台");
    }
}
