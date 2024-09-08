package com.gk.car.similarity.controllers;

import com.gk.car.similarity.dto.CarFilteringDto;
import com.gk.car.similarity.dto.CarFilteringItemDto;
import com.gk.car.similarity.dto.CarSimilarityInputDto;
import com.gk.car.similarity.dto.CarSimilarityInputItemDto;
import com.gk.car.similarity.dto.CarSimilarityOutputDto;
import com.gk.car.similarity.services.CarSimilarityService;
import com.gk.car.similarity.strategies.CarFilteringStrategy;
import com.gk.car.similarity.strategies.CarSimilarityStrategy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/housekeeping")
@RequiredArgsConstructor
public class SimilarityHousekeepingController {

  private final CarFilteringStrategy carFilteringStrategy;

  private final CarSimilarityStrategy carSimilarityStrategy;

  private final CarSimilarityService carSimilarityService;

  @GetMapping(value = "/filter")
  private CarFilteringDto fetchFilteredCars() {
    return carFilteringStrategy.filterCars();
  }

  @GetMapping(value = "/similar")
  private CarSimilarityOutputDto findSimilarCars() {
    CarFilteringDto carFilteringDto = carFilteringStrategy.filterCars();
    CarSimilarityInputDto carSimilarityInputDto = CarSimilarityInputDto.builder()
        .cars(carFilteringDto.getCars().get(0).stream().map(car -> CarSimilarityInputItemDto.builder().carVariantId(car.getCarVariantId()).build()).toList())
        .build();
    return carSimilarityStrategy.findSimilarCars(carSimilarityInputDto);
  }

  @PostMapping(value = "/filter")
  private void filterAndFindSimilar() {
    carSimilarityService.calculateSimilarities();
  }
}
