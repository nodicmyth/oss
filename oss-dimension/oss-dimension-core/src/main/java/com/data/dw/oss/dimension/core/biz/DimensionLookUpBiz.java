package com.data.dw.oss.dimension.core.biz;


import com.data.dw.oss.dimension.api.dto.Column;
import com.data.dw.oss.dimension.api.dto.DimensionRequestDTO;

public interface DimensionLookUpBiz {
    Column lookUp(DimensionRequestDTO dimensionInfo);
}