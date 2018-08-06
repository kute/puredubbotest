package com.kute.cache.redis;

import redis.clients.jedis.ShardedJedis;

public interface ShardedRedisExecutor<T> {
    T execute(ShardedJedis jedis);
}
