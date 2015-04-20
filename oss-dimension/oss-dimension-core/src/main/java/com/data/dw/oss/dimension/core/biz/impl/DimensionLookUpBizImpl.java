package com.data.dw.oss.dimension.core.biz.impl;

import com.data.dw.oss.dimension.api.dto.Column;
import com.data.dw.oss.dimension.api.dto.DimensionRequestDTO;
import com.data.dw.oss.dimension.core.biz.DimensionLookUpBiz;
import com.data.dw.oss.dimension.core.service.DimensionLookUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class DimensionLookUpBizImpl implements DimensionLookUpBiz {
    private static final Logger logger = LoggerFactory.getLogger(DimensionLookUpBizImpl.class);
    @Resource
    private DimensionLookUpService dimensionLookUpService;

    public DimensionLookUpService getDimensionLookUpService() {
        return dimensionLookUpService;
    }

    public void setDimensionLookUpService(DimensionLookUpService dimensionLookUpService) {
        this.dimensionLookUpService = dimensionLookUpService;
    }

    @Override
    public Column lookUp(DimensionRequestDTO dimensionInfo) {
        logger.info("[lookUp] DimensionInfo = [ " + dimensionInfo.toKeyString() + "]");
        return dimensionLookUpService.casDimension(dimensionInfo);
    }

}
