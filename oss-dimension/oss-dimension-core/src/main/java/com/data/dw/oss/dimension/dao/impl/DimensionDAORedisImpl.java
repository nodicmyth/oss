package com.data.dw.oss.dimension.dao.impl;

import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;
import com.data.dw.oss.redis.client.RedisClient;
import com.data.dw.oss.dimension.dao.DimensionDAO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by huangshiqian on 15/4/8.
 */
public class DimensionDAORedisImpl implements DimensionDAO {
    private static final Logger logger = LoggerFactory.getLogger(DimensionDAORedisImpl.class);

    @Resource
    private RedisClient redisClient;

//    private RedisClient redis;

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public Column save(DimensionRequestDTO dimensionInfo) {
        logger.debug("[save] Save To Redis DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
                + dimensionInfo.toValueString() + "]");
        if(!dimensionInfo.getDimKey().isBlank()) {
            redisClient.addKV(dimensionInfo.toKeyString(), dimensionInfo.toValueString());

            return dimensionInfo.getDimKey();
        }
        return null;
    }

    @Override
    public Column find(DimensionRequestDTO dimensionInfo) {
        logger.debug("[find] Find From Redis DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
                + dimensionInfo.toValueString() + "]");
        String vals = redisClient.getValue(dimensionInfo.toKeyString());

        logger.debug("[find] Find From Redis DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
                + dimensionInfo.toValueString() + "], Val = [" + vals + "]");
        Column dimKey = dimensionInfo.getDimKey();

        if(!StringUtils.isBlank(vals)) {
            try {
                String[] valArr = vals.split(":");

                dimKey.setName(valArr[0]);
                dimKey.setValue(Long.parseLong(valArr[1]));

                return dimKey;
            } catch (NumberFormatException ex) {
                logger.error("[find] Number Format Error, Redis Cache = [" + dimensionInfo.toKeyString() + ", "
                        + dimensionInfo.toValueString() + "]");
            }
        }

        return null;
    }
}
