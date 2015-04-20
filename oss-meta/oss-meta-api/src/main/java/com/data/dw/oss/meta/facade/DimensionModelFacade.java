package com.data.dw.oss.meta.facade;

import com.data.dw.oss.meta.dto.Column;
import com.data.dw.oss.meta.dto.DimensionModel;

/**
 * Created by huangshiqian on 15/4/20.
 */
public abstract class DimensionModelFacade {
    public abstract Column getDimKey(DimensionModel dimensionModel);
}
