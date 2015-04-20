package com.data.dw.oss.dimension.service;

import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;

/**
 * Created by huangshiqian on 15/4/8.
 */
public interface DimensionLookUpService {
    Column casDimension(DimensionRequestDTO dimensionInfo);
}
