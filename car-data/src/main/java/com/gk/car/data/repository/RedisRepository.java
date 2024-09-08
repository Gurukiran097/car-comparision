package com.gk.car.data.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

  private final RedisTemplate<String, String> redisTemplate;

  public void clearAndSave(String key, List<String> data) {
    redisTemplate.delete(key);
    ListOperations<String, String> listOps = redisTemplate.opsForList();
    listOps.rightPushAll(key, data);
  }

  public List<String> fetchList(String key) {
    return redisTemplate.opsForList().range(key, 0, -1);
  }
}
