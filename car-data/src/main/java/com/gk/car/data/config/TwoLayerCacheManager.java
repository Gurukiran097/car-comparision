package com.gk.car.data.config;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwoLayerCacheManager implements CacheManager {

  private final CacheManager inMemoryCacheManager;
  private final CacheManager redisCacheManager;

  public TwoLayerCacheManager(@Qualifier("caffeineCacheManager") CacheManager inMemoryCacheManager,@Qualifier("redisCacheManager") CacheManager redisCacheManager) {
      this.inMemoryCacheManager = inMemoryCacheManager;
      this.redisCacheManager = redisCacheManager;
  }

  @Override
  public TwoLayerCache getCache(String name) {
    return new TwoLayerCache(inMemoryCacheManager.getCache(name), redisCacheManager.getCache(name), name);
  }

  @Override
  public Collection<String> getCacheNames() {
    return List.of("carCache");
  }
}
