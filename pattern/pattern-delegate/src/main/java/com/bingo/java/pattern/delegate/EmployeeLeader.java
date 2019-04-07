package com.bingo.java.pattern.delegate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Bingo
 * @title: EmployeeA
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/6  12:20
 */
public class EmployeeLeader  {
    Map<String,Employee> container = new HashMap<>();
    {
        container.put("后台",new EmployeeA());
        container.put("前台",new EmployeeB());
    }
    public void doWork(String workName) {
        Optional.of(container.get(workName)).ifPresent(l->l.doWork());
    }
}
