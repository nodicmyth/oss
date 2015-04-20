package com.data.dw.oss.meta.dao.impl;


import com.data.dw.oss.meta.dao.BaseDAO;
import com.data.dw.oss.meta.dao.DimensionModelDAO;
import com.data.dw.oss.meta.dto.Column;
import com.data.dw.oss.meta.dto.ColumnType;
import com.data.dw.oss.meta.dto.DimensionModel;

/**
 * Created by huangshiqian on 15/4/8.
 */
public class DimensionModelDAOImpl extends BaseDAO implements DimensionModelDAO {
    @Override
    public Column findKeyColumn(DimensionModel dimension) {
        Column column = (Column) queryOne("com.data.dw.oss.meta.sqlmap.findDimensionKeyColumn", dimension);

        if(column != null) {
            column.setColumn_type(ColumnType.DIM_KEY);
        }

        return column;
    }

    @Override
    public void saveKeyColumn(DimensionModel dimension, Column column) {

    }
//    private static final Logger logger = LoggerFactory.getLogger(DimensionDAOImpl.class);
//
//    @Override
//    public Column save(DimensionRequestDTO dimensionInfo) {
//        logger.debug("[save] Save To DB DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
//                + dimensionInfo.toValueString() + "]");
//
//        save("com.data.dw.oss.sqlmap.createDimensionAutoKey", dimensionInfo);
//
//        if(dimensionInfo.getDimKey().isBlank()) {
//            return null;
//        }
//
//        return dimensionInfo.getDimKey();
//    }
//
//    @Override
//    public Column find(DimensionRequestDTO dimensionInfo) {
//        Column dimKey = dimensionInfo.getDimKey();
//        logger.debug("[find] Find From DB DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
//                + dimensionInfo.toValueString() + "]");
//        dimKey.setValue(queryOne("com.data.dw.oss.sqlmap.queryDimension", dimensionInfo));
//
//        if(dimKey.isBlank()) {
//            logger.debug("[find] DinKey From DB IS Blank DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
//                    + dimensionInfo.toValueString() + "]");
//            return null;
//        }
//
//        return dimKey;
//    }
}
