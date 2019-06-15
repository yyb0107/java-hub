package bingo.custom.mebatis.framework.executor;

import bingo.custom.mebatis.framework.handler.BGStatementHandler;
import bingo.custom.mebatis.framework.Configuration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bingo
 * @title: BGSimpleExecutor
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  10:30
 */
public class BGSimpleExecutor extends BGBaseExecutor {

    public BGSimpleExecutor(Configuration configuration) {
        super(configuration);
    }

    @Override
    public <T> List<T> query(String sql, Object[] args,Class<T> clazz) throws SQLException {
        Connection connection = null;
        BGStatementHandler statementHandler = configuration.newStatementHandler(sql,args);

    if(true){
        Statement statement = this.prepare(sql);
        statementHandler.parameterize(statement,args);
        return statementHandler.queryList(statement,clazz);
    }
        return new ArrayList<>();
    }


    private Statement prepare(String sql) {

        try {
            Connection connection = getConnection();
            return connection.prepareStatement(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
