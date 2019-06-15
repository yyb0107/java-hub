package bingo.custom.mebatis.framework;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Bingo
 * @title: MapperProxy
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  11:20
 */
@Slf4j
public class MapperProxy implements InvocationHandler {
    BGDefaultSqlSession sqlSession;

    public MapperProxy(BGDefaultSqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String statementId = method.getDeclaringClass().getName() + "." + method.getName();
        if (sqlSession.configuration.statementMappings.containsKey(statementId)) {
            String sql = sqlSession.configuration.statementMappings.get(statementId);
            Class<?> clazz = sqlSession.configuration.statementEntityTypeMappings(statementId);
            log.info("sql {}", sql);
            if (method.getReturnType() == List.class) {
                return sqlSession.query(sql, args, clazz);
            } else {
                Object obj = sqlSession.query(sql, args, clazz);
                if (obj instanceof List && ((List) obj).size() == 1) {
                    return ((List) obj).get(0);
                }


            }

        }
        return null;
    }
}
