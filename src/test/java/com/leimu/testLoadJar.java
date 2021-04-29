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
    public void testLoadJar() {
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
}
