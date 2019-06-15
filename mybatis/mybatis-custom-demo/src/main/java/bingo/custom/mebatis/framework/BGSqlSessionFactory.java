package bingo.custom.mebatis.framework;

/**
 * @author Bingo
 * @title: BGSqlSessionFactory
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  10:24
 */
public class BGSqlSessionFactory {
    Configuration configuration;

    public BGSqlSessionFactory() {
        configuration = new Configuration();
    }

    public BGDefaultSqlSession openSqlSession() {
        return new BGDefaultSqlSession(configuration);
    }
}
