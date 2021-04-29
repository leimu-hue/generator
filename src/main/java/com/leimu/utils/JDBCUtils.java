package com.leimu.utils;

import com.leimu.config.ConnectionConfig;
import com.leimu.constant.Constant;
import com.leimu.constant.TableConstant;
import com.leimu.database.detail.TableDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class JDBCUtils {

    private static final Logger logger = LoggerFactory.getLogger(JDBCUtils.class);

    /**
     * 获取连接
     *
     * @return
     */
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(ConnectionConfig.JdbcDriver);
        Connection connection = DriverManager.getConnection(ConnectionConfig.jdbcConnection, ConnectionConfig.username
                , ConnectionConfig.password);
        return connection;
    }

    /**
     * 获取的元数据信息主要有下面
     *  TABLE_CAT：表示数据表的目录名
     *  TABLE_SCHEM：表示数据表的模式名
     *  TABLE_NAME：表示数据表名
     *  TABLE_TYPE：表示数据表类型 视图还是其他
     *  REMARKS：表示注释
     *  TYPE_CAT：
     *  TYPE_SCHEM：
     *  TYPE_NAME：
     *  SELF_REFERENCING_COL_NAME：
     *  REF_GENERATION：
     *
     *
     * 获取所有的表信息
     */
    public static TableConstant showAllTables() throws Exception {
        TableConstant tableConstant = new TableConstant();
        Connection connection = getConnection();
        //获取所有元数据信息
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, Constant.types);
        TableDetail tableDetail;
        //进行循环获取对应的表
        while(tables.next()){
            tableDetail = new TableDetail();
            tableDetail.setTableComment(tables.getString("REMARKS"));
            tableDetail.setTableName(tables.getString("TABLE_NAME"));
            tableDetail.setTableType(tables.getString("TABLE_TYPE"));
            tableConstant.addTableDetailsInThis(tableDetail);
        }
        return tableConstant;
    }

    /**
     * 解析表中的字段
     *
     * @param table
     */
    public static void resolveColumn(String table) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        try {
            table = table.trim();
            if (table.contains(" ")) {
                logger.error("表 " + table + " 一个单词，请检查");
                return;
            }
            PreparedStatement preparedStatement = connection.prepareStatement("desc " + table);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }
}
