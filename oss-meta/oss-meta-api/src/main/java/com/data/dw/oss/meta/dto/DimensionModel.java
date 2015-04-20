package com.data.dw.oss.meta.dto;

import java.io.Serializable;

/**
 * Created by huangshiqian on 15/4/20.
 */
public class DimensionModel implements Serializable {
    private String dimensionName;


    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    @Override
    public String toString() {
        return "DimensionModel{" +
                "dimensionName='" + dimensionName + '\'' +
                '}';
    }
}
