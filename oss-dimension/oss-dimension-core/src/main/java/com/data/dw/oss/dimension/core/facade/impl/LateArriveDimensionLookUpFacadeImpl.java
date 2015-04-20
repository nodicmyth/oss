package com.data.dw.oss.dimension.core.facade.impl;

import com.data.dw.oss.dimension.api.dto.Column;
import com.data.dw.oss.dimension.api.dto.DimensionRequestDTO;
import com.data.dw.oss.dimension.api.facade.LateArriveDimensionLookUpFacade;
import com.data.dw.oss.dimension.core.biz.DimensionLookUpBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by huangshiqian on 15/4/8.
 */
public class LateArriveDimensionLookUpFacadeImpl implements LateArriveDimensionLookUpFacade {
    private static final Logger logger = LoggerFactory.getLogger(LateArriveDimensionLookUpFacadeImpl.class);

    @Resource
    private DimensionLookUpBiz dimensionLookUpBiz;

    public DimensionLookUpBiz getDimensionLookUpBiz() {
        return dimensionLookUpBiz;
    }

    public void setDimensionLookUpBiz(DimensionLookUpBiz dimensionLookUpBiz) {
        this.dimensionLookUpBiz = dimensionLookUpBiz;
    }

    @Override
    public Column lookUp(DimensionRequestDTO dimensionRequestDTO) {
        logger.info("[lookUp] DimensionRequestDTO = [ " + dimensionRequestDTO.toKeyString() + "]");
        return dimensionLookUpBiz.lookUp(dimensionRequestDTO);
    }
}
