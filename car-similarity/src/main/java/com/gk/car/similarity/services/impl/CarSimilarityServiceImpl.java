package com.gk.car.similarity.services.impl;

import static com.gk.car.commons.constants.KafkaConstants.SIMILAR_CARS_TOPIC;
import static com.gk.car.similarity.constants.Constants.CAR_TYPE_FILTER_STRATEGY;
import static com.gk.car.similarity.constants.Constants.LABEL_SIMILARITY_STRATEGY;

import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.similarity.dto.CarFilteringDto;
import com.gk.car.similarity.dto.CarFilteringItemDto;
import com.gk.car.similarity.dto.CarSimilarityInputDto;
import com.gk.car.similarity.dto.CarSimilarityInputItemDto;
import com.gk.car.similarity.dto.CarSimilarityOutputDto;
import com.gk.car.similarity.dto.CarSimilarityOutputItemDto;
import com.gk.car.similarity.factories.CarFilteringStrategyFactory;
import com.gk.car.similarity.factories.CarSimilarityStrategyFactory;
import com.gk.car.similarity.services.CarSimilarityService;
import com.gk.car.similarity.strategies.CarFilteringStrategy;
import com.gk.car.similarity.strategies.CarSimilarityStrategy;
import com.gk.car.similarity.utils.KafkaPublisher;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class CarSimilarityServiceImpl implements CarSimilarityService {

  private final CarFilteringStrategyFactory carFilteringStrategyFactory;

  private final CarSimilarityStrategyFactory carSimilarityStrategyFactory;

  private final KafkaPublisher kafkaPublisher;

  @Value("${car.filter.strategy:CarTypeFilterStrategy}")
  private String filterStrategy;
  @Value("${car.similarity.strategy:LabelBasedSimilarityStrategy}")
  private String similarityStrategy;


  @Override
  public void calculateSimilarities() {
    CarFilteringStrategy filteringStrategy = carFilteringStrategyFactory.getFilteringStrategy(filterStrategy);
    CarSimilarityStrategy carSimilarityStrategy = carSimilarityStrategyFactory.getSimilarityStrategy(similarityStrategy);

    CarFilteringDto carFilteringDto = filteringStrategy.filterCars();
    if(Objects.isNull(carFilteringDto) || Objects.isNull(carFilteringDto.getCars())) {
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, "No cars found for filtering");
    }
    carFilteringDto.getCars().stream().forEach(cars -> {
      CarSimilarityInputDto carSimilarityInputDto = CarSimilarityInputDto.builder()
          .cars(cars.stream().map(car -> CarSimilarityInputItemDto.builder().carVariantId(car.getCarVariantId()).build()).toList())
          .build();
      CarSimilarityOutputDto carSimilarityOutputDto = carSimilarityStrategy.findSimilarCars(carSimilarityInputDto);
      if(Objects.isNull(carSimilarityOutputDto) || Objects.isNull(carSimilarityOutputDto.getSimilarityMap())) {
        throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, "Similar cars invalid");
      }
      for(String car : carSimilarityOutputDto.getSimilarityMap().keySet()) {
        List<String> similarVariants = carSimilarityOutputDto.getSimilarityMap().get(car).stream().map(
            CarSimilarityOutputItemDto::getCarVariantId).toList();
        kafkaPublisher.send(SIMILAR_CARS_TOPIC, CarSimilarityUpdateDto.builder()
            .carVariantId(car)
            .similarVariants(similarVariants)
            .build());
        log.info("Car : {}, Similar cars : {}", car, carSimilarityOutputDto.getSimilarityMap().get(car));
      }
    });
  }
}
