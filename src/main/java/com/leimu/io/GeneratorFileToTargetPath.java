package com.leimu.io;

import com.leimu.config.FileBuilderOfConfig;
import com.leimu.constant.TableConstant;
import com.leimu.database.detail.TableDetail;

import java.util.HashMap;
import java.util.List;

public class GeneratorFileToTargetPath {

    private static String entityOfPackage;

    private static String controllerPackage;

    private static String servicePackage;

    private static String mapperPackage;

    private static String outputFilePath;

    /**
     * 开始准备生成文件
     *
     * @param tableConstants 需要生成的内容
     */
    public static void toGenerator(TableConstant tableConstants,FileBuilderOfConfig fileBuilderOfConfig,String content) {
        outputFilePath = fileBuilderOfConfig.getOutputFilePath();
        List<TableDetail> tableDetails = tableConstants.getTableDetails();
        tableDetails.forEach(o -> {
            //生成实体类
            generatorEntity(o);
            //生成mapper层
            generatorMapper(o);
            //生成mapper.xml文件
            generatorMapperXML(o);
            //生成service层
            generatorService(o);
            //生成controller层
            generatorController(o);
        });
    }

    /**
     * 生成实体类
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorEntity(TableDetail tableDetail) {
        String tableName = tableDetail.getTableName();
        String[] tableNameSplit = tableName.split("_");
        StringBuilder content = new StringBuilder();
        for (String temp : tableNameSplit){
            String lowerCase = temp.toLowerCase();
            content.append(lowerCase.substring(0,1).toUpperCase() + lowerCase.substring(1));
        }
        tableName = content.toString();



    }

    /**
     * 生成mapper
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorMapper(TableDetail tableDetail) {

    }

    /**
     * 生成mapper xml文件
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorMapperXML(TableDetail tableDetail) {

    }

    /**
     * 生成service层
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorService(TableDetail tableDetail) {

    }

    /**
     * 生成controller层
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorController(TableDetail tableDetail) {

    }

}
