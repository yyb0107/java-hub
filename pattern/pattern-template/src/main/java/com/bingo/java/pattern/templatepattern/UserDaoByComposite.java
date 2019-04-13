package com.bingo.java.pattern.templatepattern;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author Bingo
 * @title: UserDao
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/12  23:21
 */
public class UserDaoByComposite {
    BingoJdbcTemplate template ;

    public UserDaoByComposite(Properties ps){
        template = new BingoJdbcTemplate(ps);
    }

    public List<User> selectAll() throws SQLException, ClassNotFoundException {
        return template.query(new UserMapper(), "select * from t_user");
    }
}
