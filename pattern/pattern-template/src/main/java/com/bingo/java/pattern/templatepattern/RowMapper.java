package com.bingo.java.pattern.templatepattern;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Bingo
 * @title: RowMapper
 * @projectName java-hub
 * @description: TODO
 * @date 2019/4/12  22:54
 */
public interface RowMapper<T> {
    public T mapper(ResultSet rs) throws SQLException;
}
