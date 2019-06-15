package bingo.custom.mebatis.framework.handler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Bingo
 * @title: BGParameterHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  20:55
 */
public class BGParameterHandler {
    public Statement parameterize(Statement statement, Object[] args) throws SQLException {
        for(int i=0;args!=null && i<args.length;i++){
            ((PreparedStatement)statement).setObject(i+1, args[i]);
        }
        return statement;
    }
}
