package com.leimu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 存放数据库表等信息
 */

public class DataBaseVariable {

    private static Logger logger = LoggerFactory.getLogger(DataBaseVariable.class);

    private static List<String> tables;

    public static List<String> getTables() {
        return tables;
    }

    public static void addItemInTables(String table) {
        logger.info("加入 "+table+" 表");
        tables.add(table);
    }
}
