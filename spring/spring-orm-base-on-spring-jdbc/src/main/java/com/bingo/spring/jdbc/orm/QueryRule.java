package com.bingo.spring.jdbc.orm;

/**
 * @author Bingo
 * @title: QueryRule
 * @projectName java-hub
 */
public class QueryRule {
    Class<?> clazz;
    String sql;

    public QueryRule(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz(){
        return clazz;
    }
    public void setSql(String sql) {
        this.sql = sql;
    }

    public String toSql() {
        return sql;
    }
}
