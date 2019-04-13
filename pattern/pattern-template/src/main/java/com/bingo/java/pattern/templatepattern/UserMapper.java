package com.bingo.java.pattern.templatepattern;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bingo
 * @title: UserMapper
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/12  23:07
 */
public class UserMapper implements RowMapper<User>{
    @Override
    public User mapper(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUid(rs.getString("userid"));
        user.setUname(rs.getString("username"));
//        user.setUid(rs.getString("uid"));
        return user;
    }
}
