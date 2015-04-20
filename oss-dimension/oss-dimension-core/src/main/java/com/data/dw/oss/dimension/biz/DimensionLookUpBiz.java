package com.data.dw.oss.dimension.biz;


import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;

public interface DimensionLookUpBiz {
    Column lookUp(DimensionRequestDTO dimensionInfo);
}