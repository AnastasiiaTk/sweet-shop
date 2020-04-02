package com.anastasiiat.sweetshop.cache.config;

import com.anastasiiat.sweetshop.cache.data.BasketData;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class BasketCacheConfig {

    @Value("${basket.max.size}")
    private Integer maxSize;

    @Value("${basket.expiration.time}")
    private Integer expirationTime;

    private CacheLoader<String, BasketData> loader = new CacheLoader<String, BasketData>() {
        @Override
        public BasketData load(String sessionId) {
            return new BasketData();
        }
    };

    private LoadingCache<String, BasketData> cache;

    @PostConstruct
    public void initCache() {
        cache = CacheBuilder.newBuilder().maximumSize(maxSize).expireAfterAccess(expirationTime, TimeUnit.SECONDS).build(loader);
    }

    public LoadingCache<String, BasketData> getCache() {
        return cache;
    }
}
