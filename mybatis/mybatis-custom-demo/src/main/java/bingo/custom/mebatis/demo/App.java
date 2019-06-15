package bingo.custom.mebatis.demo;

import bingo.custom.mebatis.framework.BGDefaultSqlSession;
import bingo.custom.mebatis.framework.BGSqlSessionFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Bingo
 * @title: App
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  10:21
 */
@Slf4j
public class App {
    public static void main(String[] args) {
        BGSqlSessionFactory sqlSessionFactory = new BGSqlSessionFactory();
        BGDefaultSqlSession sqlSession = sqlSessionFactory.openSqlSession();
        UserMapper userMapper = sqlSession.getMpper(UserMapper.class);
        User user = userMapper.selectByPrimaryKey(4);
        log.info("user {}", user);
        List<User> users = userMapper.selectAll();
        for (User u : users) {
            log.info("user {}", u);
        }
    }
}
