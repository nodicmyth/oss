package com.data.dw.oss.dimension.api.facade;

import com.data.dw.oss.dimension.api.dto.Column;
import com.data.dw.oss.dimension.api.dto.DimensionRequestDTO;

/**
 * LateArriveDimensionLookUpFacade create by huangshiqian
 * dubbo调用的包装接口
 */
public interface LateArriveDimensionLookUpFacade {
	public abstract Column lookUp(DimensionRequestDTO dimensionRequestDTO);
}
