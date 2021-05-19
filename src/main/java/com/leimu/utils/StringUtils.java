package com.leimu.utils;

import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isEmpty(String content) {
        return content == null || "".equalsIgnoreCase(content);
    }


    /**
     * 将 content_test 更改成为ContentTest
     *
     * @param name 需要更改的内容
     * @return 更改之后的内容
     */
    public static String toConvertName(String name) {
        String[] tableNameSplit = name.split("_");
        StringBuilder content = new StringBuilder();
        for (String temp : tableNameSplit) {
            String lowerCase = temp.toLowerCase();
            content.append(lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1));
        }
        return content.toString();
    }

    /**
     * 处理字符串 将所有空白字符去除
     *
     * @param content 需要处理的字符
     * @return 处理过的字符
     */
    public static String toHandlerContent(String content) {
        Pattern compile = Pattern.compile("\\s+");
        return compile.matcher(content).replaceAll("");
    }

    /**
     * 小写首字母
     *
     * @param content 需要处理的字符
     * @return 处理过的字符
     */
    public static String toLowerFirstChar(String content) {
        char firstChar = content.charAt(0);
        char lower = (char) (firstChar ^ (1 << 5));
        return content.replaceFirst(firstChar + "", lower + "");
    }

}
