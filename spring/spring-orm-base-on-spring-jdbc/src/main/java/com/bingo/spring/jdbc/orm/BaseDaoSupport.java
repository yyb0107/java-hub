package com.bingo.spring.jdbc.orm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Column;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Bingo
 * @title: BaseDaoSupport
 * @projectName java-hub
 */
@Slf4j
public abstract class BaseDaoSupport<T extends Serializable, PK extends Serializable> implements BaseDao<T, PK> {
    public abstract String getPKColumn();

    private JdbcTemplate jdbcTemplate;
    private DataSource datasource;
    private Class<T> clazz;

    public RowMapper getRowMapper(Class<?> clazz) {
        RowMapper rowMapper = (rs, index) -> {
            Field[] fields = clazz.getDeclaredFields();
            Object obj = null;
            String columnName = null;
            try {
                obj = clazz.newInstance();
                for (Field field : fields) {
                    columnName = field.getName();
                    if (field.isAnnotationPresent(Column.class)) {
                        columnName = field.getAnnotation(Column.class).name();
                    }
                    log.info(columnName);
                    field.setAccessible(true);
                    field.set(obj, rs.getObject(columnName));
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return obj;
        };
        return rowMapper;

    }


    public BaseDaoSupport() {

    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<T> select(QueryRule queryRule) throws Exception {
        return jdbcTemplate.query(queryRule.toSql(), getRowMapper(queryRule.getClazz()));
    }

    @Override
    public Page<?> select(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectBySql(String sql, Object... args) throws Exception {
        return null;
    }

    @Override
    public Page<Map<String, Object>> selectBySqlToPage(String sql, Object[] param, int pageNo, int pageSize) throws Exception {
        return null;
    }

    @Override
    public boolean delete(T entity) throws Exception {
        return false;
    }

    @Override
    public int deleteAll(List<T> list) throws Exception {
        return 0;
    }

    @Override
    public PK insertAndReturnId(T entity) throws Exception {
        return null;
    }

    @Override
    public boolean insert(T entity) throws Exception {
        return false;
    }

    @Override
    public int insertAll(List<T> list) throws Exception {
        return 0;
    }

    @Override
    public boolean update(T entity) throws Exception {
        return false;
    }
}
