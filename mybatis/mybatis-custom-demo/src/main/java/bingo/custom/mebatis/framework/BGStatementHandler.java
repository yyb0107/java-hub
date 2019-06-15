package bingo.custom.mebatis.framework;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Bingo
 * @title: BGStatementHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  13:32
 */
public class BGStatementHandler {
    BGParameterHandler parameterHandler;
    BGResultSetHandler resultSetHandler;
    Configuration configuration;

    public BGStatementHandler(Configuration configuration){
        this.configuration = configuration;
        parameterHandler = new BGParameterHandler();
        resultSetHandler = new BGResultSetHandler();
    }


    public <T> List<T> queryList(Statement statement, Class<T> clazz){
        try {
            ((PreparedStatement)statement).execute();
            return this.resultSetHandler.handleResultSets(statement.getResultSet(),clazz);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T query(Statement statement,Class<T> clazz) {
        List<T> result = queryList(statement, clazz);
        if(!result.isEmpty()){
            return queryList(statement, clazz).get(0);
        }
        return null;
    }

    public Statement parameterize(Statement statement, Object[] args){
        try {
            return this.parameterHandler.parameterize(statement,args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
