package bingo.custom.mebatis.framework;

import bingo.custom.mebatis.framework.executor.BGExecutor;
import bingo.custom.mebatis.framework.executor.BGSimpleExecutor;
import bingo.custom.mebatis.framework.handler.BGStatementHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Bingo
 * @title: Configuration
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  10:24
 */
@Slf4j
public class Configuration {
    public final ResourceBundle datasource = ResourceBundle.getBundle("datasource");
    ResourceBundle config = ResourceBundle.getBundle("config");

    final Map<String,String> statementMappings = new HashMap<>();
    final Map<String,String> statementEntityTypeMappings = new HashMap<>();

    public Configuration(){
        config.keySet().forEach(key->{
            if(key.contains("Mapper")){
                statementMappings.computeIfAbsent(key, l -> config.getString(key));
                statementEntityTypeMappings.computeIfAbsent(key,l->config.getString(key).split("-- ")[1]);
            }
        });
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        String driverClass = configuration.datasource.getString("jdbc.driverClass");
        log.info("driverClass {}", driverClass);
    }

    public <T> T getMapper(Class<T> clazz, BGDefaultSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MapperProxy(sqlSession));
    }

    public BGExecutor newExecutor() {
        return new BGSimpleExecutor(this);
    }

    public BGStatementHandler newStatementHandler(String sql, Object[] args) {
        return new BGStatementHandler(this);
    }

    public Class<?> statementEntityTypeMappings(String statementId) {
        try {
            return Class.forName(statementEntityTypeMappings.get(statementId));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
