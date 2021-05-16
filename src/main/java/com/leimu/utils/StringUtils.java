package com.leimu.utils;

public class StringUtils {

    public static boolean isEmpty(String content) {
        return content == null || "".equalsIgnoreCase(content);
    }


    /**
     * 将 content_test 更改成为ContentTest
     * @param name 需要更改的内容
     * @return 更改之后的内容
     */
    public static String toConvertName(String name){
        String[] tableNameSplit = name.split("_");
        StringBuilder content = new StringBuilder();
        for (String temp : tableNameSplit){
            String lowerCase = temp.toLowerCase();
            content.append(lowerCase.substring(0,1).toUpperCase() + lowerCase.substring(1));
        }
        return content.toString();
    }

}
