package com.leimu.utils;

public class StringUtils {

    public static boolean isEmpty(String content) {
        return content == null || "".equalsIgnoreCase(content);
    }

}
