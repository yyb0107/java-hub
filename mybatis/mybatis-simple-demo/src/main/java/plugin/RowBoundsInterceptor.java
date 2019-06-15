package plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author Bingo
 * @title: RowBoundsInterceptor
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/12  20:29
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
@Slf4j
public class RowBoundsInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Executor executor = (Executor) invocation.getTarget();
        Object[] objs = invocation.getArgs();
        int offset = 0;
        int limit = 10;
        BoundSql boundSql = null;
        MappedStatement ms = (MappedStatement) objs[0];
        Object parameter = objs[1];
        RowBounds rowBounds = (RowBounds) objs[2];
        ResultHandler rh = (ResultHandler) objs[3];
        CacheKey cacheKey = null;
        if (objs.length == 4) {
            boundSql = ms.getBoundSql(parameter);
            offset = rowBounds.getOffset();
            limit = rowBounds.getLimit();
            log.info("offset {} limit {}",offset,limit);
            String sql = boundSql.getSql() + String.format(" LIMIT %s, %s ",offset,limit);
            boundSql = new BoundSql(ms.getConfiguration(),sql,boundSql.getParameterMappings(),parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey) objs[4];
            boundSql = (BoundSql) objs[5];
        }
        return executor.query(ms, parameter, rowBounds, rh, cacheKey, boundSql);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
