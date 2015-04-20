package com.data.dw.meta.model;

import java.io.Serializable;

/**
 * Created by huangshiqian on 15/4/20.
 */
public class DimensionModel implements Serializable {
    private String dimensionName;
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
}
