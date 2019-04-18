package com.bingo.java.spring.customize.mvc.servlet;

import java.io.File;

/**
 * @author Bingo
 * @title: Test
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/18  0:02
 */
public class Test {

    public static void main(String[] args) {
        String packagePath = Test.class.getClassLoader().getResource("").getPath()+"/"+ "com.bingo.test".replace(".","/");
//        File file = new File(packagePath);
//        System.out.println(packagePath);
    }


}
