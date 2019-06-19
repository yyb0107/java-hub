package demo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import generate.dao.UserMapper;
import generate.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
    public void selectWithPage() {
        PageHelper.offsetPage(5,5);
        List<User> users  = userMapper.selectAll();
        log.info("users size {}", users.size());
        for (User user : users) {
            log.info("userid {}, username {}",user.getUserid(),user.getUsername());
        }
        PageInfo pageInfo = new PageInfo<User>(users);
        log.info(pageInfo.toString());
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

    @Test
    public void insert() {
        String[] names = new String[]{"Bingo", "Frank", "Erric", "Mic", "James", "Tony", "Gray", "Summer", "Leo"};
        for (int i=0;i<names.length;i++) {
            User user = new User();
            user.setUserid(i+2);
            user.setUsername(names[i]);
            int rs = userMapper.insert(user);
            log.info("rs {}", rs);
        }
        sqlSession.commit();
    }

    @Test
    public void testSelectRowBounds(){
        RowBounds rowBounds = new RowBounds(5,3);
        List<User> users = userMapper.selectAll(rowBounds);
        log.info("users size {}", users.size());
        for (User user : users) {
            log.info("userid {}, username {}",user.getUserid(),user.getUsername());
        }

    }

}
