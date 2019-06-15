package bingo.custom.mebatis.framework.executor;

import bingo.custom.mebatis.framework.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Bingo
 * @title: BGExecutor
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/15  10:29
 */
public abstract class BGBaseExecutor implements BGExecutor {
    Configuration configuration;

    public BGBaseExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(configuration.datasource.getString("jdbc.driverClass"));
        String url = configuration.datasource.getString("jdbc.url");
        String username = configuration.datasource.getString("jdbc.username");
        String password = configuration.datasource.getString("jdbc.password");

        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }
}
