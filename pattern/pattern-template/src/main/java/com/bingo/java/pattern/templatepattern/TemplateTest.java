package com.bingo.java.pattern.templatepattern;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author Bingo
 * @title: TemplateTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/12  23:14
 */
public class TemplateTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Properties ps = new Properties();
        ps.setProperty("driverClassName", "com.mysql.jdbc.Driver");
        ps.setProperty("url", "jdbc:mysql://localhost/db1");
        ps.setProperty("username", "root");
        ps.setProperty("password", "password");

        UserDao userDao =new UserDao(ps);
        List<User> result = userDao.selectAll();
        for (User user : result) {
            System.out.println(user);
        }

    }

}
