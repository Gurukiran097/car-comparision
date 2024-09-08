package com.gk.car.similarity.strategies;

import com.gk.car.similarity.dto.CarSimilarityInputDto;
import com.gk.car.similarity.dto.CarSimilarityOutputDto;

public interface CarSimilarityStrategy {

  CarSimilarityOutputDto findSimilarCars(CarSimilarityInputDto carSimilarityInputDto);
}
