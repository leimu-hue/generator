package com.leimu.utils;

import com.leimu.database.detail.ColumnDetail;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主要用于生成文件的内容
 */
public class FileContentGeneratorUtils {

    private static final String method = "\t${permission} ${type} ${methodName}(${args}){";

    private static final String resultMapContentOfId = "\t\t<id property=\"${propertyType}\" column=\"${columnType}\" />";

    private static final String resultMapContentOfResult = "\t\t<result property=\"${propertyType}\" column=\"${columnType}\" />";


    public static String generatorPackageContent(String content) {
        return "package " + content + ";";
    }

    public static String generatorImportContent(List<ColumnDetail> columnDetails) {
        StringBuilder importContent = new StringBuilder();
        String regex = "java.lang";
        List<String> isReat = new ArrayList<>();
        for (ColumnDetail columnDetail : columnDetails) {
            Class<?> columnOfJavaType = columnDetail.getColumnOfJavaType();
            String name;
            if (columnOfJavaType.isArray()) {
                String temp = columnOfJavaType.getName();
                name = temp.substring(temp.indexOf("L") + 1, temp.lastIndexOf("."));
            } else {
                name = columnOfJavaType.getPackage().getName();
            }
            if (!StringUtils.isEmpty(name) && !name.startsWith(regex)) {
                if (columnOfJavaType.isArray()) {
                    name = columnOfJavaType.getName();
                    name = name.substring(name.indexOf("L") + 1, name.length() - 1);
                } else {
                    name = columnOfJavaType.getName();
                }
                //如果已经包含，就不需要再次设置了
                if (isReat.contains(name.trim())) {
                    continue;
                }
                importContent.append("import ");
                importContent.append(name);
                isReat.add(name.trim());
                importContent.append(";");
                importContent.append("\n");
            }
        }
        return importContent.toString();
    }

    public static String generatorFieldContent(String type, String content) {
        return "\tprivate " + type + " " + content + ";";
    }

    /**
     * 生成 set方法
     *
     * @param write      输出流
     * @param permission 访问权限
     * @param fieldName  字段名字
     * @throws IOException 抛出异常
     */
    public static void generatorSetMethodContent(BufferedWriter write, String permission,
                                                 String type,
                                                 String fieldName) throws IOException {
        String content = method.replace("${permission}", permission)
                .replace("${type}", "void").replace("${methodName}", "set" + fieldName)
                .replace("${args}", type + " " + StringUtils.toLowerFirstChar(fieldName));
        write.write(content);
        write.newLine();
        content = "this." + StringUtils.toLowerFirstChar(fieldName) + " = " + StringUtils.toLowerFirstChar(fieldName) + ";";
        write.write(content);
        write.newLine();
        write.write("}");
        write.newLine();
        write.newLine();
    }

    /**
     * @param writer     输出流
     * @param permission 权限
     * @param fieldName  字段名字
     * @throws IOException 抛出异常
     */
    public static void generatorGetMethodContent(BufferedWriter writer, String permission,
                                                 String type,
                                                 String fieldName) throws IOException {
        String content = method.replace("${permission}", permission)
                .replace("${type}", type).replace("${methodName}", "get" + fieldName)
                .replace("${args}", "");
        writer.write(content);
        writer.newLine();
        content = "return this." + StringUtils.toLowerFirstChar(fieldName) + ";";
        writer.write(content);
        writer.newLine();
        writer.write("}");
        writer.newLine();
        writer.newLine();
    }

    /**
     * 生成mapper.xml文件的resultMap内容
     *
     * @param writer        输出流
     * @param columnDetails 字段信息
     * @throws IOException 抛出异常
     */
    public static void generatorMapperXmlOfResultMap(BufferedWriter writer, List<ColumnDetail> columnDetails) throws IOException {
        if (columnDetails == null || columnDetails.size() == 0) {
            return;
        }
        for (ColumnDetail columnDetail : columnDetails) {
            String columnName = columnDetail.getColumnName();
            String javaName = StringUtils.toLowerFirstChar(StringUtils.toConvertName(columnName));
            String content;
            if (columnDetail.getColumnIsPrimaryKey()) {
                content = resultMapContentOfId;
            } else {
                content = resultMapContentOfResult;
            }
            content = content.replace("${propertyType}", javaName).replace("${columnType}", columnName);
            writer.write(content);
            writer.newLine();
        }
    }

}