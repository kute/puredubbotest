package com.kute.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * created by kute on 2018/05/16 22:39
 */
public class LocalCacheUtil<K, V> {

    private static final LocalCacheUtil<Object, Object> INSTANCE = new LocalCacheUtil<>();

    private LocalCacheUtil() {
    }

    public static LocalCacheUtil<Object, Object> getINSTANCE() {
        return INSTANCE;
    }

    private final LoadingCache<K, V> CACHE = CacheBuilder.newBuilder()
//    设置并发级别为8，并发级别是指可以同时写缓存的线程数
            .concurrencyLevel(10)
            .maximumSize(500)
//            设置缓存容器的初始容量为10
            .initialCapacity(10)
            // 5s 内无读写自动失效移除
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .recordStats()
            .build(
                    // 当缓存未命中时，通过此重新设置缓存，注意：此不能返回 null
                    new CacheLoader<K, V>() {
                        @Override
                        public V load(K key) throws Exception {
                            return null;
                        }
                    });

    public V get(K key) throws ExecutionException {
        return CACHE.get(key);
    }


}
