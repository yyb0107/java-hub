package demo;

import generate.dao.UserMapper;
import generate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Bingo
 * @title: UserDaoTest
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/4  22:43
 */
@Slf4j
public class UserDaoTest {
    SqlSessionFactory sqlSessionFactory;
    SqlSession sqlSession;
    UserMapper userMapper;

    @Before
    public void before() {
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(UserDaoDemo.class.getClassLoader().getResourceAsStream("mybatis-config.xml"));
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void update() {
        User user = new User();
        user.setUserid(1);
        user.setUsername("Tom");
        int rs = userMapper.updateByPrimaryKey(user);
        sqlSession.commit();
        log.info("rs {}", rs);
    }

}
