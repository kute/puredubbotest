package com.kute.cache.redis;

import java.util.ResourceBundle;

public class SharedRedisConfig {
    
    private static final String DEFAULT_REDIS_PROPERTIES = "shardedRedis";
    private static ResourceBundle REDIS_CONFIG = ResourceBundle.getBundle(DEFAULT_REDIS_PROPERTIES);

    public static String getConfigProperty(String key) {
        return REDIS_CONFIG.getString(key);
    }
}
