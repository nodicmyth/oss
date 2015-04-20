package com.data.dw.oss.dimension.service.impl;

import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;
import com.data.dw.oss.dimension.dao.DimensionDAO;
import com.data.dw.oss.dimension.service.DimensionLookUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by huangshiqian on 15/4/8.
 */
public class DimensionLookUpServiceImpl implements DimensionLookUpService {
    private static final Logger logger = LoggerFactory.getLogger(DimensionLookUpServiceImpl.class);
    @Resource
    private DimensionDAO dimensionDAO;
    @Resource
    private DimensionDAO dimensionCacheDAO;

    public DimensionDAO getDimensionDAO() {
        return dimensionDAO;
    }

    public void setDimensionDAO(DimensionDAO dimensionDAO) {
        this.dimensionDAO = dimensionDAO;
    }

    public DimensionDAO getDimensionCacheDAO() {
        return dimensionCacheDAO;
    }

    public void setDimensionCacheDAO(DimensionDAO dimensionCacheDAO) {
        this.dimensionCacheDAO = dimensionCacheDAO;
    }

    @Override
    public Column casDimension(DimensionRequestDTO dimensionInfo) {
        logger.info("[casDimension] Scan From Redis, DimensionInfo = [ " + dimensionInfo.toKeyString() + "]");
        // 从缓存中查询代理键
        Column key = dimensionCacheDAO.find(dimensionInfo);

        logger.info("[casDimension] Scan From Redis, DimensionInfo = [ " + dimensionInfo.toKeyString() + "]");
        // 缓存中没有则从DB中查询,若DB中也没有,则插入维度纪录,然后返回新生成的代理键
        // 并将代理键同步到缓存系统
        if(key == null || key.isBlank()) {
            logger.info("[casDimension] Scan From DB, DimensionInfo = [ " + dimensionInfo.toKeyString() + "]");


            key = dimensionDAO.find(dimensionInfo);

            if(key == null || key.isBlank()) {
                logger.info("[casDimension] Save To DB, DimensionInfo = [ " + dimensionInfo.toKeyString() + ","
                        + dimensionInfo.toValueString() + "]");

                dimensionDAO.save(dimensionInfo);

                key = dimensionInfo.getDimKey();
            }

            logger.info("[casDimension] Save To Redis, DimensionInfo = [ " + dimensionInfo.toKeyString() + ","
                    + dimensionInfo.toValueString() + "]");
            dimensionCacheDAO.save(dimensionInfo);
        }

        return key;
    }
}