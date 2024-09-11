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
  public CarFilteringDto fetchFilteredCars() {
    return carFilteringStrategy.filterCars();
  }

  @PostMapping(value = "/filter")
  public void filterAndFindSimilar() {
    carSimilarityService.calculateSimilarities();
  }
}
