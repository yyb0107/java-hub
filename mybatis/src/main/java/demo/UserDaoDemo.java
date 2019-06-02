package demo;

import generate.dao.UserMapper;
import generate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author Bingo
 * @title: UserDaoDemo
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/2  22:23
 */
@Slf4j
public class UserDaoDemo {
    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(UserDaoDemo.class.getClassLoader().getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUserid(1);
        user.setUsername("Tom");
        int rs = userMapper.insert(user);
        sqlSession.commit();
        log.info("rs {}", rs);

    }
}
