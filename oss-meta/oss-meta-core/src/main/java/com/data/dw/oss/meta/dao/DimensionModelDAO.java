package com.data.dw.oss.meta.dao;


import com.data.dw.oss.meta.dto.Column;
import com.data.dw.oss.meta.dto.DimensionModel;

/**
 * Created by huangshiqian on 15/4/8.
 */
public interface DimensionModelDAO {
    Column findKeyColumn(DimensionModel dimensionName);

    void saveKeyColumn(DimensionModel dimensionName, Column column);
}
