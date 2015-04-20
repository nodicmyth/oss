package com.data.dw.oss.meta.facade.impl;


import com.data.dw.oss.meta.dto.Column;
import com.data.dw.oss.meta.dto.DimensionModel;
import com.data.dw.oss.meta.facade.DimensionModelFacade;
import com.data.dw.oss.meta.service.DimensionModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by huangshiqian on 15/4/8.
 */
public class DimensionModelFacadeImpl implements DimensionModelFacade {
    private static final Logger logger = LoggerFactory.getLogger(DimensionModelFacadeImpl.class);

    @Resource
    private DimensionModelService dimensionModelService;

    public DimensionModelService getDimensionModelService() {
        return dimensionModelService;
    }

    public void setDimensionModelService(DimensionModelService dimensionModelService) {
        this.dimensionModelService = dimensionModelService;
    }

    @Override
    public Column getDimKey(DimensionModel dimensionModel) {
        logger.info("[getDimKey] DimensionName = " + dimensionModel.getDimensionName());
        Column col = dimensionModelService.getDimKey(dimensionModel);
        return col;
    }
}
