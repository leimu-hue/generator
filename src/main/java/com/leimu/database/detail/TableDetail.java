package com.leimu.database.detail;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放对应表的基本信息
 *
 * @author haiqinhuang
 */
public class TableDetail {

    private String tableName;

    private String tableComment;

    private String tableType;

    private List<ColumnDetail> columnDetails;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<ColumnDetail> getColumnDetails() {
        return columnDetails;
    }

    public void setColumnDetails(List<ColumnDetail> columnDetails) {
        this.columnDetails = columnDetails;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public void addColumnDetailInThis(ColumnDetail columnDetail){
        if (this.columnDetails==null){
            this.columnDetails = new ArrayList<>();
        }
        this.getColumnDetails().add(columnDetail);
    }
}
