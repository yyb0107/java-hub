package com.bingo.spring.jdbc.demo;

import com.bingo.spring.jdbc.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Bingo
 * @title: JdbcTemplateTest
 * @projectName java-hub
 */
@Slf4j
public class JdbcTemplateTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        DataSource dataSource = (DataSource)context.getBean("dataSource");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<User> users = jdbcTemplate.query("select * from t_user", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUserId(resultSet.getInt("userid"));
                user.setUsername(resultSet.getString("username"));
                return user;
            }
        });
        for (User user : users) {
         log.info(""+user);
        }
    }

}
