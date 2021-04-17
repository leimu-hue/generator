package com.leimu.utils;

import com.leimu.config.ConnectionConfig;
import com.leimu.config.DataBaseVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    private static Logger logger = LoggerFactory.getLogger(JDBCUtils.class);

    /**
     * 获取连接
     * @return
     */
    private static Connection getConnection() throws SQLException,ClassNotFoundException {
        Connection connection = null;
        Class.forName(ConnectionConfig.JdbcDriver);
        connection = DriverManager.getConnection(ConnectionConfig.jdbcConnection,ConnectionConfig.username
                ,ConnectionConfig.password);
        return connection;
    }

    public static void showAllTables(){
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException throwables) {
            logger.error(throwables.getSQLState()+throwables.getErrorCode(),throwables);
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error(e.getLocalizedMessage(),e);
            e.printStackTrace();
        }
        try {
            PreparedStatement show_tables = connection.prepareStatement("show tables");
            ResultSet resultSet = show_tables.executeQuery();
            while (resultSet.next()){
                DataBaseVariable.addItemInTables(resultSet.getString(1));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }

    }

    /**
     * 解析表中的字段
     * @param table
     */
    public static void resolveColumn(String table){
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        try {
            table = table.trim();
            if (table.contains(" ")){
                logger.error("表 "+table+" 一个单词，请检查");
                return;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("desc " + table);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){

            }
        } catch (SQLException e) {
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }

    }
}
