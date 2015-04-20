package com.data.dw.oss.dimension.core.service;

import com.data.dw.oss.dimension.api.dto.Column;
import com.data.dw.oss.dimension.api.dto.DimensionRequestDTO;

/**
 * Created by huangshiqian on 15/4/8.
 */
public interface DimensionLookUpService {
    Column casDimension(DimensionRequestDTO dimensionInfo);
}
