package com.gk.car.similarity.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPublisher {

  private final ObjectMapper objectMapper;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public void send(String topic, String key, Object message) {
    try {
      kafkaTemplate.send(topic, key, objectMapper.writeValueAsString(message));
    } catch (JsonProcessingException exception) {
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, exception.getMessage());
    }
  }

  public void send(String topic, Object message) {
    try {
      kafkaTemplate.send(topic, objectMapper.writeValueAsString(message));
    } catch (JsonProcessingException exception) {
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, exception.getMessage());
    }
  }
}
