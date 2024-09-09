package com.gk.car.data.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.car.commons.constants.KafkaConstants;
import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.data.services.impl.CarWriteServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CarSimilarityUpdateListener {

  private final CarWriteServiceImpl carManagementService;

  private final ObjectMapper objectMapper;

  @KafkaListener(
      topics = KafkaConstants.SIMILAR_CARS_TOPIC,
      containerFactory = "kafkaListenerContainerFactory"
  )
  public void consume(@Payload String message, Acknowledgment acknowledgment) {
    try {
      log.info("Received message in topic {}, message {}", KafkaConstants.SIMILAR_CARS_TOPIC, message);
      carManagementService.updateSimilarCars(objectMapper.readValue(message, CarSimilarityUpdateDto.class));
      acknowledgment.acknowledge();
    } catch (JsonProcessingException exception) {
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, exception.getMessage());
    }

  }

}
