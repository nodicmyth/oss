package com.data.dw.oss.dimension.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by huangshiqian on 15/4/8.
 */
public class DimensionRequestDTO implements Serializable {
    /**
     * 纬度表名称
     */
    private String dimensionName;
    /**
     * 纬度表属性和值,属性对应了表中的列,通过值来匹配
     */
    private Set<Column> columns;
    /**
     * 维度代理键
     */
    private Column dimKey;

    public Column getDimKey() {
        return dimKey;
    }

    public void setDimKey(Column dimKey) {
        this.dimKey = dimKey;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }


    public String toKeyString() {
//        JSON.toJSONString(info)
        StringBuffer sbuf = new StringBuffer("{").append(dimensionName).append(":[");
        for(Column col : columns) {
            sbuf.append(col.getName()).append(":").append(col.getValue()).append("],[");
        }
        sbuf.delete(sbuf.length() - 2, sbuf.length());

        sbuf.append("}");
        return sbuf.toString();
    }

    public String toValueString() {
        return new StringBuffer(dimKey.getName()).append(":").append(dimKey.getValue()).toString();
    }

}
