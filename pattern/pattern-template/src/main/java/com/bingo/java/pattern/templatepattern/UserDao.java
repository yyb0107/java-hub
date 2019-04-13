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
public class UserDao extends BingoJdbcTemplate {
    public UserDao(Properties proerties) {
        super(proerties);
    }

    public List<User> selectAll() throws SQLException, ClassNotFoundException {
        return query(new UserMapper(), "select * from t_user");
    }
}
