package com.leimu.utils;

import com.leimu.config.ConnectionConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    /**
     * 获取连接
     * @return
     */
    private Connection getConnection() throws SQLException,ClassNotFoundException {
        Connection connection = null;
        Class.forName(ConnectionConfig.JdbcDriver);
        connection = DriverManager.getConnection(ConnectionConfig.jdbcConnection,ConnectionConfig.username
                ,ConnectionConfig.password);
        return connection;
    }

}
