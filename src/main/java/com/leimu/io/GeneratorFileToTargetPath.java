package com.leimu.io;

import com.leimu.config.FileBuilderOfConfig;
import com.leimu.constant.TableConstant;
import com.leimu.database.detail.ColumnDetail;
import com.leimu.database.detail.TableDetail;
import com.leimu.utils.FileContentGeneratorUtils;
import com.leimu.utils.FileWriteUtils;
import com.leimu.utils.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GeneratorFileToTargetPath {

    private static String entityOfPackage;

    private static String entityOfName;

    /**
     * 基础的名字
     * 比如Generator
     * 实体类为GeneratorEntity
     */
    private static String baseJavaName;

    private static String controllerPackage;

    private static String controllerOfName;

    private static String servicePackage;

    private static String serviceOfName;

    private static String mapperPackage;

    private static String mapperOfName;

    private static String mapperXmlPackage;

    private static String mapperXmlName;

    private static String outputFilePath;

    private static FileBuilderOfConfig fileBuilderOfConfig;

    /**
     * 开始准备生成文件
     *
     * @param tableConstants 需要生成的内容
     */
    public static void toGenerator(TableConstant tableConstants, FileBuilderOfConfig fileBuilderOfConfig, String content) {
        outputFilePath = fileBuilderOfConfig.getOutputFilePath();
        GeneratorFileToTargetPath.fileBuilderOfConfig = fileBuilderOfConfig;
        List<TableDetail> tableDetails = tableConstants.getTableDetails();
        tableDetails.forEach(o -> {
            //在这里需要将所有需要生成的类 包名和类名设置完毕
            if (setAllPackageAndClassName(o.getTableName())) {
                //生成实体类
                try {
                    generatorEntity(o);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }
                //生成mapper层
                try {
                    generatorMapper(o);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //生成mapper.xml文件
                try {
                    generatorMapperXML(o);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //生成service层
                try {
                    generatorService(o);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //生成controller层
                try {
                    generatorController(o);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 设置生成属性的基本信息
     *
     * @param tableName 表的名字
     * @return 返回当前的表名字是否为空
     */
    private static boolean setAllPackageAndClassName(String tableName) {
        if (StringUtils.isEmpty(tableName)) {
            return false;
        }
        tableName = StringUtils.toConvertName(tableName);
        String basePackage = fileBuilderOfConfig.getBasePackage();
        //设置基础名字
        GeneratorFileToTargetPath.baseJavaName = tableName;
        //设置实体类的基础信息
        GeneratorFileToTargetPath.entityOfPackage = basePackage + ".entity";
        GeneratorFileToTargetPath.entityOfName = tableName + "Entity.java";
        //生成mapper的基础信息
        GeneratorFileToTargetPath.mapperPackage = basePackage + ".mapper";
        GeneratorFileToTargetPath.mapperOfName = tableName + "Mapper.java";
        //生成service的基础信息
        GeneratorFileToTargetPath.servicePackage = basePackage + ".service";
        GeneratorFileToTargetPath.serviceOfName = tableName + "Service.java";
        //生成controller的基础信息
        GeneratorFileToTargetPath.controllerPackage = basePackage + ".controller";
        GeneratorFileToTargetPath.controllerOfName = tableName + "Controller.java";
        //生成xml文件所在位置
        GeneratorFileToTargetPath.mapperXmlPackage = basePackage + ".xml";
        GeneratorFileToTargetPath.mapperXmlName = tableName + "Mapper.xml";
        return true;
    }

    /**
     * 生成实体类
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorEntity(TableDetail tableDetail) throws IOException {
        //设置路径
        String path = FileWriteUtils.createPath(outputFilePath, GeneratorFileToTargetPath.entityOfPackage);
        //设置输出流路径
        FileWriteUtils.setOutputPath(path, GeneratorFileToTargetPath.entityOfName);
        BufferedWriter write = FileWriteUtils.getWrite();
        //首先写入package内容
        if (!StringUtils.isEmpty(GeneratorFileToTargetPath.entityOfPackage)) {
            String packageContent = FileContentGeneratorUtils.
                    generatorPackageContent(GeneratorFileToTargetPath.entityOfPackage);
            FileWriteUtils.toWriteLineContentInFile(write, packageContent);
        }
        //其次生成import的内容
        if (tableDetail.getColumnDetails().size() > 0) {
            String importContent = FileContentGeneratorUtils.generatorImportContent(tableDetail.getColumnDetails());
            FileWriteUtils.toWriteLineContentInFile(write, importContent);
        }
        //生成文件内容
        String contentEntity = "public class " + GeneratorFileToTargetPath.
                entityOfName.substring(0, entityOfName.length() - 5) + "{";
        FileWriteUtils.toWriteLineContentInFile(write, contentEntity);
        //输出字段
        List<String> setAndGetGenerator = new ArrayList<>();
        for (ColumnDetail columnDetail : tableDetail.getColumnDetails()) {
            String javaTypeName = columnDetail.getColumnOfJavaType().getSimpleName();
            String javaName = StringUtils.toConvertName(columnDetail.getColumnName());
            setAndGetGenerator.add(javaTypeName + "_" + javaName);
            String fieldContent = FileContentGeneratorUtils.generatorFieldContent(javaTypeName, StringUtils.toLowerFirstChar(javaName));
            FileWriteUtils.toWriteLineContentInFile(write, fieldContent);
        }
        //输出字段的set和get方法
        for (String method : setAndGetGenerator) {
            String type = method.split("_")[0];
            String name = method.split("_")[1];
            FileContentGeneratorUtils.generatorGetMethodContent(write,
                    "public", type, name);
            FileContentGeneratorUtils.generatorSetMethodContent(write,
                    "public", type, name);
        }
        FileWriteUtils.toWriteLineContentInFile(write, "}");
        write.flush();
        write.close();
    }

    /**
     * 生成mapper
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorMapper(TableDetail tableDetail) throws IOException {
        //设置路径
        String path = FileWriteUtils.createPath(outputFilePath,
                GeneratorFileToTargetPath.mapperPackage);
        //设置输出流路径
        FileWriteUtils.setOutputPath(path, GeneratorFileToTargetPath.mapperOfName);
        BufferedWriter write = FileWriteUtils.getWrite();
        //首先写入package内容
        if (!StringUtils.isEmpty(GeneratorFileToTargetPath.mapperPackage)) {
            String packageContent = FileContentGeneratorUtils.generatorPackageContent(GeneratorFileToTargetPath.mapperPackage);
            FileWriteUtils.toWriteLineContentInFile(write, packageContent);
        }
        //生成文件内容
        String contentMapper = "public interface " + GeneratorFileToTargetPath.
                mapperOfName.substring(0, mapperOfName.length() - 5) + "{";
        FileWriteUtils.toWriteLineContentInFile(write, contentMapper);
        //结尾
        FileWriteUtils.toWriteLineContentInFile(write, "}");
        write.flush();
        write.close();
    }


    /**
     * 生成mapper xml文件
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorMapperXML(TableDetail tableDetail) throws IOException {
        //设置xml模板的文本位置
        InputStream inputStream = GeneratorFileToTargetPath.class.getClassLoader().getResourceAsStream("TemplateMapper.xml");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        //设置输出文件的路径
        String path = FileWriteUtils.createPath(outputFilePath,
                GeneratorFileToTargetPath.mapperXmlPackage);
        FileWriteUtils.setOutputPath(path, GeneratorFileToTargetPath.mapperXmlName);
        BufferedWriter write = FileWriteUtils.getWrite();
        String content = "";
        while ((content = bufferedReader.readLine()) != null) {
            if (content.trim().startsWith("<mapper")) {
                content = content.replace("${namespace}",
                        GeneratorFileToTargetPath.mapperPackage + "." + GeneratorFileToTargetPath.
                                mapperOfName.substring(0, mapperOfName.length() - 5));
            } else if (content.trim().startsWith("<resultMap")) {
                content = content.replace("${resultMapId}", baseJavaName).
                        replace("${resultMapType}",
                                GeneratorFileToTargetPath.entityOfPackage + "." + GeneratorFileToTargetPath.
                                        entityOfName.substring(0, entityOfName.length() - 5));
            } else if (content.trim().contains("${content}")) {
                content = "";
                FileContentGeneratorUtils.generatorMapperXmlOfResultMap(write, tableDetail.getColumnDetails());
            } else if (content.trim().contains("${sql}")) {
                content = "";
            }
            write.write(content);
            write.newLine();
        }
        //关闭流
        bufferedReader.close();
        write.flush();
        write.close();
    }

    /**
     * 生成service层
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorService(TableDetail tableDetail) throws IOException {
        //设置路径
        String path = FileWriteUtils.createPath(outputFilePath,
                GeneratorFileToTargetPath.servicePackage);
        //设置输出流路径
        FileWriteUtils.setOutputPath(path, GeneratorFileToTargetPath.serviceOfName);
        BufferedWriter write = FileWriteUtils.getWrite();
        //首先写入package内容
        if (!StringUtils.isEmpty(GeneratorFileToTargetPath.servicePackage)) {
            String packageContent = FileContentGeneratorUtils.generatorPackageContent(GeneratorFileToTargetPath.servicePackage);
            FileWriteUtils.toWriteLineContentInFile(write, packageContent);
        }
        //生成文件内容
        String contentMapper = "public interface " + GeneratorFileToTargetPath.
                serviceOfName.substring(0, serviceOfName.length() - 5) + "{";
        FileWriteUtils.toWriteLineContentInFile(write, contentMapper);
        //结尾
        FileWriteUtils.toWriteLineContentInFile(write, "}");
        write.flush();
        write.close();
    }

    /**
     * 生成controller层
     *
     * @param tableDetail 需要生成的内容
     */
    private static void generatorController(TableDetail tableDetail) throws IOException {
        //设置路径
        String path = FileWriteUtils.createPath(outputFilePath,
                GeneratorFileToTargetPath.controllerPackage);
        //设置输出流路径
        FileWriteUtils.setOutputPath(path, GeneratorFileToTargetPath.controllerOfName);
        BufferedWriter write = FileWriteUtils.getWrite();
        //首先写入package内容
        if (!StringUtils.isEmpty(GeneratorFileToTargetPath.controllerPackage)) {
            String packageContent = FileContentGeneratorUtils.generatorPackageContent(GeneratorFileToTargetPath.controllerPackage);
            FileWriteUtils.toWriteLineContentInFile(write, packageContent);
        }
        //生成文件内容
        String contentMapper = "public interface " + GeneratorFileToTargetPath.
                controllerOfName.substring(0, controllerOfName.length() - 5) + "{";
        FileWriteUtils.toWriteLineContentInFile(write, contentMapper);
        //结尾
        FileWriteUtils.toWriteLineContentInFile(write, "}");
        write.flush();
        write.close();
    }

}
