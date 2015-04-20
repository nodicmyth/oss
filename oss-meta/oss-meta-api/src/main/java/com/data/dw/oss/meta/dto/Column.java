package com.data.dw.oss.meta.dto;

import java.io.Serializable;

/**
 * Created by huangshiqian on 15/4/20.
 */
public class Column implements Serializable {
    private String column_name;
    private ColumnType column_type;
    private String column_data_type;

    public String getColumn_data_type() {
        return column_data_type;
    }

    public void setColumn_data_type(String column_data_type) {
        this.column_data_type = column_data_type;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public ColumnType getColumn_type() {
        return column_type;
    }

    public void setColumn_type(ColumnType column_type) {
        this.column_type = column_type;
    }

    @Override
    public String toString() {
        return "Column{" +
                "column_data_type='" + column_data_type + '\'' +
                ", column_name='" + column_name + '\'' +
                ", column_type=" + column_type +
                '}';
    }
}
