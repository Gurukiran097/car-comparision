package com.gk.car.data.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;

public class TwoLayerCache implements Cache {

  private static final Logger logger = LoggerFactory.getLogger(TwoLayerCache.class);

  private final Cache inMemoryCache;
  private final Cache redisCache;

  public TwoLayerCache(Cache inMemoryCache, Cache redisCache) {
    this.inMemoryCache = inMemoryCache;
    this.redisCache = redisCache;
  }

  @Override
  public String getName() {
    return inMemoryCache.getName();
  }

  @Override
  public Object getNativeCache() {
    return inMemoryCache.getNativeCache();
  }

  @Override
  public ValueWrapper get(Object key) {
    ValueWrapper value = inMemoryCache.get(key);
    if (value == null) {
      logger.info("Cache miss in in-memory cache for key: {}", key);
      value = redisCache.get(key);
      if (value != null) {
        logger.info("Cache hit in Redis cache for key: {}", key);
        inMemoryCache.put(key, value.get());
      }
    } else {
      logger.info("Cache hit in in-memory cache for key: {}", key);
    }
    return value;
  }

  @Override
  public <T> T get(Object key, Class<T> type) {
    T value = inMemoryCache.get(key, type);
    if (value == null) {
      logger.info("Cache miss in in-memory cache for key: {}", key);
      value = redisCache.get(key, type);
      if (value != null) {
        logger.info("Cache hit in Redis cache for key: {}", key);
        inMemoryCache.put(key, value);
      }
    } else {
      logger.info("Cache hit in in-memory cache for key: {}", key);
    }
    return value;
  }

  @Override
  public <T> T get(Object key, Callable<T> valueLoader) {
    throw new UnsupportedOperationException("Callable not supported");
  }

  @Override
  public void put(Object key, Object value) {
    logger.info("Putting value in both caches for key: {}", key);
    inMemoryCache.put(key, value);
    redisCache.put(key, value);
  }

  @Override
  public void evict(Object key) {
    logger.info("Evicting value from both caches for key: {}", key);
    inMemoryCache.evict(key);
    redisCache.evict(key);
  }

  @Override
  public void clear() {
    logger.info("Clearing both caches");
    inMemoryCache.clear();
    redisCache.clear();
  }
}