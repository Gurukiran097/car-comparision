package com.gk.car.management.config;

import com.gk.car.management.clients.CarDataClient;
import feign.Feign;
import feign.Request;
import feign.Request.Options;
import feign.Retryer;
import feign.Target;
import feign.Target.HardCodedTarget;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

  @Value(value = "${urls.carDataUrl}")
  private String carDataUrl;

  @Bean
  public CarDataClient carDataClient() {
    return Feign.builder()
        .client(new OkHttpClient())
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .options(new Options(10000, 10000))
        .retryer(Retryer.NEVER_RETRY)
        .target(new HardCodedTarget<>(CarDataClient.class, carDataUrl));
  }
}
