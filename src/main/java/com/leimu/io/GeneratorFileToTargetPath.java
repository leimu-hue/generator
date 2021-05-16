package com.leimu.io;

import com.leimu.config.FileBuilderOfConfig;
import com.leimu.constant.TableConstant;
import com.leimu.database.detail.ColumnDetail;
import com.leimu.database.detail.TableDetail;
import com.leimu.utils.FileContentGeneratorUtils;
import com.leimu.utils.FileWriteUtils;
import com.leimu.utils.StringUtils;
import sun.net.www.protocol.file.FileURLConnection;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneratorFileToTargetPath {

    private static String entityOfPackage;

    private static String controllerPackage;

    private static String servicePackage;

    private static String mapperPackage;

    private static String outputFilePath;

    private static FileBuilderOfConfig fileBuilderOfConfig;

    /**
     * 开始准备生成文件
     *
     * @param tableConstants 需要生成的内容
     */
    public static void toGenerator(TableConstant tableConstants,FileBuilderOfConfig fileBuilderOfConfig,String content) {
        outputFilePath = fileBuilderOfConfig.getOutputFilePath();
        GeneratorFileToTargetPath.fileBuilderOfConfig = fileBuilderOfConfig;
        List<TableDetail> tableDetails = tableConstants.getTableDetails();
        tableDetails.forEach(o -> {
            //生成实体类
            try {
                generatorEntity(o);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
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
    private static void generatorEntity(TableDetail tableDetail) throws IOException {
        String tableName = StringUtils.toConvertName(tableDetail.getTableName());
        String basePackage = fileBuilderOfConfig.getBasePackage();
        //设置路径
        String path = FileWriteUtils.createPath(outputFilePath, basePackage + ".entity");
        //设置输出流路径
        FileWriteUtils.setOutputPath(path,tableName+".java");
        BufferedWriter write = FileWriteUtils.getWrite();
        //首先写入package内容
        if (!StringUtils.isEmpty(basePackage)){
            String packageContent = FileContentGeneratorUtils.generatorPackageContent(basePackage + ".entity");
            entityOfPackage = packageContent.replace("package ","");
            FileWriteUtils.toWriteLineContentInFile(write,packageContent);
        }
        //其次生成import的内容
        if (tableDetail.getColumnDetails().size()>0){
            String importContent = FileContentGeneratorUtils.generatorImportContent(tableDetail.getColumnDetails());
            FileWriteUtils.toWriteLineContentInFile(write,importContent);
        }
        //生成文件内容
        String contentEntity = "public class "+tableName + "{";
        FileWriteUtils.toWriteLineContentInFile(write,contentEntity);
        //输出字段
        List<String> setAndGetGenerator = new ArrayList<>();
        for (ColumnDetail columnDetail : tableDetail.getColumnDetails()){
            String javaTypeName = columnDetail.getColumnOfJavaType().getSimpleName();
            String javaName = StringUtils.toConvertName(columnDetail.getColumnName());
            setAndGetGenerator.add(javaTypeName + "_" + javaName);
            String fieldContent = FileContentGeneratorUtils.generatorFieldContent(javaTypeName, javaName);
            FileWriteUtils.toWriteLineContentInFile(write,fieldContent);
        }
        //输出字段的set和get方法
        for (String method : setAndGetGenerator){
            String type = method.split("_")[0];
            String name = method.split("_")[1];
            FileContentGeneratorUtils.generatorGetMethodContent(write,
                    "public",type,name);
            FileContentGeneratorUtils.generatorSetMethodContent(write,
                    "public",type,name);
        }
        FileWriteUtils.toWriteLineContentInFile(write,"}");
        write.flush();
        write.close();
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
