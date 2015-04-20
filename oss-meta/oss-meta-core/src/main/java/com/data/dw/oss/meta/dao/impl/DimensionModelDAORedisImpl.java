package com.data.dw.oss.meta.dao.impl;


import com.data.dw.oss.meta.dao.BaseDAO;
import com.data.dw.oss.meta.dao.DimensionModelDAO;
import com.data.dw.oss.meta.dto.Column;
import com.data.dw.oss.meta.dto.ColumnType;
import com.data.dw.oss.meta.dto.DimensionModel;
import com.data.dw.oss.redis.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by huangshiqian on 15/4/8.
 */
public class DimensionModelDAORedisImpl extends BaseDAO implements DimensionModelDAO {
    private static final Logger logger = LoggerFactory.getLogger(DimensionModelDAORedisImpl.class);
    @Resource
    private RedisClient jedisClient;

    public RedisClient getJedisClient() {
        return jedisClient;
    }

    public void setJedisClient(RedisClient jedisClient) {
        this.jedisClient = jedisClient;
    }

    @Override
    public Column findKeyColumn(DimensionModel dimension) {
//        logger.debug("[save] Save To Redis DimensionInfo = [" + dimensionInfo.toKeyString() + ", "
//                + dimensionInfo.toValueString() + "]");
        return jedisClient.getObject(dimension.getDimensionName(), ColumnType.DIM_KEY.toString(), Column.class);
    }

    @Override
    public void saveKeyColumn(DimensionModel dimension, Column column) {
        jedisClient.setObject(dimension.getDimensionName(), ColumnType.DIM_KEY.toString(), column);
    }
}
