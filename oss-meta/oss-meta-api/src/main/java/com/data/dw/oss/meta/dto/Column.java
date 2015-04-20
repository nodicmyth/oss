package com.data.dw.oss.meta.dto;

import java.io.Serializable;

/**
 * Created by huangshiqian on 15/4/20.
 */
public class Column implements Serializable {
    private String columnName;
    private ColumnType columnType;
    private String dataType;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
