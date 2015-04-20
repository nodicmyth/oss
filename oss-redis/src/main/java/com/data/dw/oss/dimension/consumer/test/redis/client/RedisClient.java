package com.data.dw.oss.dimension.consumer.test.redis.client;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by huangshiqian on 15/4/13.
 */
public class RedisClient {
    private ShardedJedis jedis;
    private ShardedJedisPool redisClientBuilder;
    private String storeKey;
    private int time_to_live = 24 * 60;

    public ShardedJedis getJedis() {
        return jedis;
    }

    public void setJedis(ShardedJedis jedis) {
        this.jedis = jedis;
    }

    public ShardedJedisPool getRedisClientBuilder() {
        return redisClientBuilder;
    }

    public void setRedisClientBuilder(ShardedJedisPool redisClientBuilder) {
        this.redisClientBuilder = redisClientBuilder;
    }

    public int getTime_to_live() {
        return time_to_live;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }

    public void setTime_to_live(int time_to_live) {
        this.time_to_live = time_to_live;
    }

    public void save() {
    }

    public void addKV(String mapKey, String mapVal) {
        ShardedJedis client = redisClientBuilder.getResource();

        client.set(mapKey, mapVal);
        client.expire(mapKey, time_to_live);

        redisClientBuilder.returnResource(client);
    }

    public String getValue(String mapKey) {
//        return jedis.hget(storeKey, mapKey);
        ShardedJedis client = redisClientBuilder.getResource();
        String value = client.get(mapKey);
        redisClientBuilder.returnResource(client);
        return value;
    }
}
