package bingo.custom.mebatis.framework;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Bingo
 * @title: BGDefaultSqlSession
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  10:28
 */
public class BGDefaultSqlSession {

    Configuration configuration;
    BGExecutor executor;

    public BGDefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = this.configuration.newExecutor();
    }

    public <T> T getMpper(Class<T> clazz) {
        return configuration.getMapper(clazz,this);
    }

    public <T> List<T> query(String sql, Object[] args, Class<T> clazz) throws SQLException {
        return executor.query(sql,args,clazz);
    }
}
