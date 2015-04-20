package com.data.dw.oss.dimension.dao.impl;

import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dao.BaseDAO;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;
import com.data.dw.oss.dimension.dao.DimensionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huangshiqian on 15/4/8.
 */
public class DimensionDAOImpl extends BaseDAO implements DimensionDAO {
    private static final Logger logger = LoggerFactory.getLogger(DimensionDAOImpl.class);

    @Override
    public Column save(DimensionRequestDTO dimensionInfo) {
        logger.debug("[save] Save To DB DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
                + dimensionInfo.toValueString() + "]");

        save("com.data.dw.oss.sqlmap.createDimensionAutoKey", dimensionInfo);

        if(dimensionInfo.getDimKey().isBlank()) {
            return null;
        }

        return dimensionInfo.getDimKey();
    }

    @Override
    public Column find(DimensionRequestDTO dimensionInfo) {
        Column dimKey = dimensionInfo.getDimKey();
        logger.debug("[find] Find From DB DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
                + dimensionInfo.toValueString() + "]");
        dimKey.setValue(queryOne("com.data.dw.oss.sqlmap.queryDimension", dimensionInfo));

        if(dimKey.isBlank()) {
            logger.debug("[find] DinKey From DB IS Blank DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
                    + dimensionInfo.toValueString() + "]");
            return null;
        }

        return dimKey;
    }
}
