package com.leimu.database.detail;

/**
 * 存放字段的描述信息
 *
 * @author haiqinhuang
 */

public class ColumnDetail {

    private String columnName;

    private String columnType;

    private Boolean columnIsNull;

    private Boolean columnIsPrimaryKey;

    private String columnComment;

    private Class<?> columnOfJavaType;

    public Class<?> getColumnOfJavaType() {
        return columnOfJavaType;
    }

    public void setColumnOfJavaType(Class<?> columnOfJavaType) {
        this.columnOfJavaType = columnOfJavaType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public Boolean getColumnIsNull() {
        return columnIsNull;
    }

    public void setColumnIsNull(Boolean columnIsNull) {
        this.columnIsNull = columnIsNull;
    }

    public Boolean getColumnIsPrimaryKey() {
        return columnIsPrimaryKey;
    }

    public void setColumnIsPrimaryKey(Boolean columnIsPrimaryKey) {
        this.columnIsPrimaryKey = columnIsPrimaryKey;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
