自己编写插件：

1、当我们传入RowBounds做翻页查询的时候，使用limit物理分页，代替原来的逻辑分页
```java
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
```
`mybatis-config.xml`
```xml
<plugins>
    <plugin interceptor="plugin.RowBoundsInterceptor"></plugin>
</plugins>
```
`UserDaoTest.java —— testSelectByRowBounds()`


2、在未启用日志组件的情况下，输出执行的SQL，并且统计SQL的执行时间（先实现查询的拦截）
```xml
<plugins>
    <plugin interceptor="plugin.SqlPrinterInterceptor"></plugin>
    <plugin interceptor="plugin.RowBoundsInterceptor"></plugin>
</plugins>
```
注意顺序，否则分页拦截的sql无法体现
```java
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
```
