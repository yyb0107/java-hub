package com.bingo.java.pattern.delegate;

import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author Bingo
 * @title: Boss
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/6  12:20
 */
public class Boss {
    public static void main(String[] args) {
        EmployeeLeader leader = new EmployeeLeader();
        leader.doWork("后台");
        DispatcherServlet d = new DispatcherServlet();
    }
}
