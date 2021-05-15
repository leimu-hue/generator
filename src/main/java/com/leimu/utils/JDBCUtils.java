package com.leimu.utils;

import com.leimu.config.ConnectionConfig;
import com.leimu.constant.Constant;
import com.leimu.constant.TableConstant;
import com.leimu.database.detail.ColumnDetail;
import com.leimu.database.detail.TableDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUtils {

    private static final Logger logger = LoggerFactory.getLogger(JDBCUtils.class);

    /**
     * @return 获取连接
     */
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(ConnectionConfig.JdbcDriver);
        return DriverManager.getConnection(ConnectionConfig.jdbcConnection, ConnectionConfig.username
                , ConnectionConfig.password);
    }

    /**
     *
     * @return 获取数据库类型
     */
    public static String getDatabaseType() {
        String jdbcConnection = ConnectionConfig.jdbcConnection;
        String substring = jdbcConnection.substring(jdbcConnection.indexOf(":") + 1);
        return substring.substring(0, jdbcConnection.indexOf(":"));
    }

    /**
     * 获取的元数据信息主要有下面
     * TABLE_CAT：表示数据表的目录名
     * TABLE_SCHEM：表示数据表的模式名
     * TABLE_NAME：表示数据表名
     * TABLE_TYPE：表示数据表类型 视图还是其他
     * REMARKS：表示注释
     * TYPE_CAT：
     * TYPE_SCHEM：
     * TYPE_NAME：
     * SELF_REFERENCING_COL_NAME：
     * REF_GENERATION：
     * <p>
     * <p>
     * 获取所有的表信息
     */
    public static TableConstant showAllTables() throws Exception {
        TableConstant tableConstant = new TableConstant();
        Connection connection = getConnection();
        String catalog = connection.getCatalog();
        String schema = connection.getSchema();
        //获取所有元数据信息
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(catalog, schema, null, Constant.types);
        TableDetail tableDetail;
        //进行循环获取对应的表
        while (tables.next()) {
            tableDetail = new TableDetail();
            tableDetail.setTableComment(tables.getString("REMARKS"));
            tableDetail.setTableName(tables.getString("TABLE_NAME"));
            tableDetail.setTableType(tables.getString("TABLE_TYPE"));
            tableConstant.addTableDetailsInThis(tableDetail);
            logger.info("读取表："+tableDetail.getTableName()+"，类型为："+tableDetail.getTableType());
        }
        return tableConstant;
    }

    /**
     * 获取数据表的所有字段信息
     * @param tableConstant 存放所有存储数据的容器
     * @param type 表示数据库类型
     * @return 返回表的详细数据
     * @throws Exception 抛出异常
     */
    public static TableConstant showAllColumnInTable(TableConstant tableConstant, String type) throws Exception {
        Connection connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        for (TableDetail tableDetail : tableConstant.getTableDetails()) {
            ResultSet columns;
            ResultSet primaryKeys;
            //根据数据库种类来虎丘字段数据和主键数据
            if ("oracle".equalsIgnoreCase(type)) {
                columns = metaData.getColumns(null, metaData.getUserName().toUpperCase(), tableDetail.getTableName(), null);
                primaryKeys = metaData.getPrimaryKeys(null, metaData.getUserName().toUpperCase(), tableDetail.getTableName());
            } else {
                columns = metaData.getColumns(null, null, tableDetail.getTableName(), null);
                primaryKeys = metaData.getPrimaryKeys(null, null, tableDetail.getTableName());
            }
            //存放主键名字
            List<String> primaryNames = new ArrayList<>();
            while (primaryKeys.next()) {
                primaryNames.add(primaryKeys.getString("COLUMN_NAME").toLowerCase());
            }

            ColumnDetail columnDetail;
            while (columns.next()) {
                columnDetail = new ColumnDetail();
                String column_name = columns.getString("COLUMN_NAME");
                boolean contains = primaryNames.contains(column_name.toLowerCase());
                String remarks = columns.getString("REMARKS");
                String is_nullable = columns.getString("IS_NULLABLE");
                String type_name = columns.getString("TYPE_NAME");

                columnDetail.setColumnName(column_name);
                columnDetail.setColumnType(type_name);
                columnDetail.setColumnComment(remarks);
                columnDetail.setColumnIsPrimaryKey(contains);
                columnDetail.setColumnIsNull("YES".equalsIgnoreCase(is_nullable));

                tableDetail.addColumnDetailInThis(columnDetail);
            }
        }
        return tableConstant;
    }
}
