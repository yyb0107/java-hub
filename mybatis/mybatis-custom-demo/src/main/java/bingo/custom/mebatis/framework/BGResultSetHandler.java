package bingo.custom.mebatis.framework;

import bingo.custom.mebatis.demo.User;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bingo
 * @title: BGResultSetHandler
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  21:18
 */
public class BGResultSetHandler {

    public <T> List<T> handleResultSets(ResultSet rs,Class<T> clazz) throws SQLException {
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            result.add(toObject(clazz, rs));
        }
        if (!result.isEmpty()) {
            return result;
        }
        return new ArrayList<>();
    }

    public <T> T toObject(Class<T> clazz, ResultSet rs) {
        Field[] fields = clazz.getDeclaredFields();
        Object obj = null;
        String columnName = null;
        try {
            obj = clazz.newInstance();
            for (Field field : fields) {
                columnName = field.getName();
                field.setAccessible(true);
                field.set(obj, rs.getObject(columnName));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (T)obj;
    }
}
