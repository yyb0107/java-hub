package com.bingo.spring.jdbc.demo;

import com.bingo.spring.jdbc.demo.dao.UserDao;
import com.bingo.spring.jdbc.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Bingo
 * @title: UserDaoTest
 * @projectName java-hub
 */
@Slf4j
public class UserDaoTest {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource);
        List<User> users = userDao.selectAll();
        for (User user : users) {
            log.info(user.toString());
        }

    }
}
