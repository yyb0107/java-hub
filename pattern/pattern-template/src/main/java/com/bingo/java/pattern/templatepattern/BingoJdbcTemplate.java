package com.bingo.java.pattern.templatepattern;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Bingo
 * @title: BingoJdbcTemplate
 * @projectName java-hub
 */
public abstract class BingoJdbcTemplate {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    private Connection connection;
    private PreparedStatement preparedStatement;

    public BingoJdbcTemplate(Properties proerties){
        driverClassName = proerties.getProperty("driverClassName");
        url = proerties.getProperty("url");
        username = proerties.getProperty("username");
        password = proerties.getProperty("password");
    }


    //1. 获取db connection

    protected Connection connection() throws ClassNotFoundException, SQLException {
        Class clazz = Class.forName("");
        Connection connection = DriverManager.getConnection("url", "username", "password");
        this.connection = connection;
        return connection;
    }

    public PreparedStatement preparedStatement(String sql) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        this.preparedStatement = ps;
        return ps;
    }

    public void close() {
        try {
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public <T> List<T> query(RowMapper<T> mapper, String sql) throws SQLException, ClassNotFoundException {
        connection();
        PreparedStatement ps = preparedStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<T> list = new ArrayList<T>();
        while (rs.next()) {
            list.add(mapper.mapper(rs));
        }
        close();
        return list;
    }

}
