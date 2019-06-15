package bingo.custom.mebatis.framework;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Bingo
 * @title: BGExecutor
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  10:29
 */
public interface BGExecutor {
    public <T> List<T> query(String sql, Object[] args, Class<T> clazz) throws SQLException;
}
