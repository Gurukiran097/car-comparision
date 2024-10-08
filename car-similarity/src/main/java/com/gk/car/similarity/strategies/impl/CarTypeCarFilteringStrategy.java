package com.gk.car.similarity.strategies.impl;

import static com.gk.car.similarity.constants.Constants.CAR_TYPE_FILTER_STRATEGY;

import com.gk.car.commons.entities.CarMetadataEntity;
import com.gk.car.commons.entities.CarVariantEntity;
import com.gk.car.commons.enums.CarType;
import com.gk.car.commons.repository.CarMetadataRepository;
import com.gk.car.commons.repository.CarVariantRepository;
import com.gk.car.similarity.dto.CarFilteringDto;
import com.gk.car.similarity.dto.CarFilteringItemDto;
import com.gk.car.similarity.strategies.CarFilteringStrategy;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(CAR_TYPE_FILTER_STRATEGY)
@Slf4j
public class CarTypeCarFilteringStrategy implements CarFilteringStrategy {

  private final CarMetadataRepository carMetadataRepository;

  private final CarVariantRepository carVariantRepository;

  @Override
  public CarFilteringDto filterCars() {
    List<List<CarFilteringItemDto>> carMetadataList = new ArrayList<>();
    for(CarType carType : CarType.values()) {
      List<CarMetadataEntity> cars = carMetadataRepository.findAllByCarType(carType);
      if(cars.isEmpty()) continue;
      List<String> carIds = cars.stream().map(CarMetadataEntity::getCarId).toList();
      List<CarVariantEntity> carVariants = carVariantRepository.findAllByCarIdIn(carIds);
      if(carVariants.isEmpty()) continue;
      carMetadataList.add(carVariants.stream().map(car -> CarFilteringItemDto.builder().carVariantId(car.getVariantId()).build()).toList());
    }
    log.info("Filtered cars based on car type {}", carMetadataList);
    return CarFilteringDto.builder().cars(carMetadataList).build();
  }
}
