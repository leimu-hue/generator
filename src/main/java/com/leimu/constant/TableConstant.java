package com.leimu.constant;

import com.leimu.database.detail.TableDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放数据库表信息
 */
public class TableConstant {

    private List<TableDetail> tableDetails;

    public List<TableDetail> getTableDetails() {
        return tableDetails;
    }

    public void setTableDetails(List<TableDetail> tableDetails) {
        this.tableDetails = tableDetails;
    }

    public void addTableDetailsInThis(TableDetail tableDetail) {
        if (this.tableDetails == null) {
            this.tableDetails = new ArrayList<>();
        }
        this.tableDetails.add(tableDetail);
    }
}
