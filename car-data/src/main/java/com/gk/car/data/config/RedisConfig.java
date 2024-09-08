package com.gk.car.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

  @Value(value = "${spring.redis.hostName}")
  private String host;

  @Value(value = "${spring.redis.port}")
  private int port;

  @Value(value = "${spring.redis.pass}")
  private String password;


  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
    redisStandaloneConfiguration.setPassword(password);
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }
}
