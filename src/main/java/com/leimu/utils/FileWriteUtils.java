package com.leimu.utils;

import java.io.*;
import java.util.regex.Matcher;

public class FileWriteUtils {

    public static String outputPath;

    public static void setOutputPath(String path, String fileName) {
        outputPath = (path + File.separator + fileName);
    }

    public static String createPath(String path, String basePackage) {
        if (basePackage.contains(".")) {
            basePackage = basePackage.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
        }
        path = path + File.separator + basePackage;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static BufferedWriter getWrite() throws IOException {
        File file = new File(outputPath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        return bufferedWriter;
    }

    /**
     * 将整块内容写入文件
     *
     * @param content 需要被写入的内容
     */
    public static void toWriteFile(String content) throws IOException {
        BufferedWriter write = getWrite();
        write.write(content);
        write.close();
    }

    public static void toWriteLineContentInFile(BufferedWriter writer, String lineContent) throws IOException {
        writer.write(lineContent);
        writer.newLine();
        writer.newLine();
    }

}
