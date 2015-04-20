package com.data.dw.oss.dimension.biz.impl;

import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;
import com.data.dw.oss.dimension.biz.DimensionLookUpBiz;
import com.data.dw.oss.dimension.service.DimensionLookUpService;
import com.data.dw.oss.meta.dto.DimensionModel;
import com.data.dw.oss.meta.facade.DimensionModelFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class DimensionLookUpBizImpl implements DimensionLookUpBiz {
    private static final Logger logger = LoggerFactory.getLogger(DimensionLookUpBizImpl.class);
    @Resource
    private DimensionLookUpService dimensionLookUpService;

    @Resource
    private DimensionModelFacade dimensionModelFacade;

//    @Resource
//    private DimensionModelService dimensionModelService;
//
//    public DimensionModelService getDimensionModelService() {
//        return dimensionModelService;
//    }
//
//    public void setDimensionModelService(DimensionModelService dimensionModelService) {
//        this.dimensionModelService = dimensionModelService;
//    }

    public DimensionModelFacade getDimensionModelFacade() {
        return dimensionModelFacade;
    }

    public void setDimensionModelFacade(DimensionModelFacade dimensionModelFacade) {
        this.dimensionModelFacade = dimensionModelFacade;
    }

    public DimensionLookUpService getDimensionLookUpService() {
        return dimensionLookUpService;
    }

    public void setDimensionLookUpService(DimensionLookUpService dimensionLookUpService) {
        this.dimensionLookUpService = dimensionLookUpService;
    }

    @Override
    public Column lookUp(DimensionRequestDTO dimensionInfo) {
        DimensionModel model = new DimensionModel();
        model.setDimensionName(dimensionInfo.getDimensionName());

        com.data.dw.oss.meta.dto.Column dimKey = dimensionModelFacade.getDimKey(model);
//        com.data.dw.oss.meta.dto.Column dimKey = dimensionModelService.getDimKey(model);

        if(dimKey == null) {
            return null;
        } else {
            dimensionInfo.setDimKey(new Column(dimKey.getColumn_name()));
        }

        logger.info("[lookUp] DimensionInfo = [ " + dimensionInfo.toKeyString() + "]");
        return dimensionLookUpService.casDimension(dimensionInfo);
    }

}
