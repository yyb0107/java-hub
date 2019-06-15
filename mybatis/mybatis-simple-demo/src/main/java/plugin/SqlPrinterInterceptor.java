package plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author Bingo
 * @title: SqlPrinterInterceptor
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/12  22:46
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
)})
@Slf4j
public class SqlPrinterInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = null;
        Executor executor = (Executor) invocation.getTarget();
        Object[] objs = invocation.getArgs();
        BoundSql boundSql = null;
        MappedStatement ms = (MappedStatement) objs[0];
        Object parameter = objs[1];
        RowBounds rowBounds = (RowBounds) objs[2];
        ResultHandler rh = (ResultHandler) objs[3];
        CacheKey cacheKey = null;
        if (objs.length == 4) {
            boundSql = ms.getBoundSql(parameter);
            cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey) objs[4];
            boundSql = (BoundSql) objs[5];
        }
        long start = System.currentTimeMillis();
        result = executor.query(ms, parameter, rowBounds, rh, cacheKey, boundSql);
        long timeTotal = System.currentTimeMillis()-start;
        log.info("SQL[{}]",boundSql.getSql().replaceAll("\\s+"," "));
        log.info("total times: {}ms",timeTotal);
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
