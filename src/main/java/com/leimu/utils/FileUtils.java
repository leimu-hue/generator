package com.leimu.utils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileUtils {

    public static String outputPath;

    public void setOutputPath(){

    }

    /**
     * 将内容写入文件
     * @param content 需要被写入的内容
     */
    public static void toWriteFile(String content) throws FileNotFoundException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath)));
    }

}
