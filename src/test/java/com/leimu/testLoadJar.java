package com.leimu;

import com.leimu.config.ConnectionConfig;
import com.leimu.constant.ConstantTest;
import com.leimu.constant.TableConstant;
import com.leimu.utils.JDBCUtils;
import com.leimu.utils.LoadJar;
import org.junit.Test;

/**
 * 测试加载是否生效
 *
 * @author haiqinhuang
 */
public class testLoadJar {

    /**
     * 进行测试
     */
    @Test
    public void testLoadJar1() {
        try {
            LoadJar.loadJar(ConstantTest.test);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAllTables() {
        ConnectionConfig.jdbcConnection = "jdbc:mysql://127.0.0.1:3306/sys?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        ConnectionConfig.JdbcDriver = "com.mysql.cj.jdbc.Driver";
        ConnectionConfig.username = "root";
        ConnectionConfig.password = "123456";
        try {
            LoadJar.loadJar(ConstantTest.test);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            JDBCUtils.showAllTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAllColumns() {
        ConnectionConfig.jdbcConnection = "jdbc:mysql://127.0.0.1:3306/sys?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        ConnectionConfig.JdbcDriver = "com.mysql.cj.jdbc.Driver";
        ConnectionConfig.username = "root";
        ConnectionConfig.password = "123456";
        try {
            LoadJar.loadJar(ConstantTest.test);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            TableConstant tableConstant = JDBCUtils.showAllTables();
            JDBCUtils.showAllColumnInTable(tableConstant, "mysql");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getAllTableAndColumn() {
        ConnectionConfig.jdbcConnection = "jdbc:mysql://127.0.0.1:3306/account?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        ConnectionConfig.JdbcDriver = "com.mysql.cj.jdbc.Driver";
        ConnectionConfig.username = "root";
        ConnectionConfig.password = "123456";
        try {
            LoadJar.loadJar(ConstantTest.test);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            TableConstant tableConstant = JDBCUtils.showAllTables();
            tableConstant = JDBCUtils.showAllColumnInTable(tableConstant, "mysql");

            System.out.println(tableConstant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
