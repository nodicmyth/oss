package com.data.dw.oss.meta.service;

import com.data.dw.oss.meta.dto.Column;
import com.data.dw.oss.meta.dto.DimensionModel;

/**
 * Created by huangshiqian on 15/4/8.
 */
public interface DimensionModelService {
    Column getDimKey(DimensionModel dimensionInfo);
}
