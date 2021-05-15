package com.leimu.config;

import java.util.List;

/**
 * 构造文件所需要的文件路径
 */

public class FileBuilderOfConfig {

    /**
     * 文件输出位置
     */
    private String outputFilePath;

    /**
     * 作者
     */
    private String author;

    /**
     * 需要被过滤的table
     * 以regex形式填充
     */
    @Deprecated
    private List<String> filterTables;

    /**
     * 设置需要被读取的table
     */
    @Deprecated
    private List<String> readTables;

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public List<String> getFilterTables() {
        return filterTables;
    }

    public void setFilterTables(List<String> filterTables) {
        this.filterTables = filterTables;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> addFilterTablesThings(String filterTables) {
        this.getFilterTables().add(filterTables);
        return this.getFilterTables();
    }

    public List<String> getReadTables() {
        return readTables;
    }

    public void setReadTables(List<String> readTables) {
        this.readTables = readTables;
    }

    public List<String> addReadTablesThings(String readTables) {
        this.getReadTables().add(readTables);
        return this.getReadTables();
    }
}
