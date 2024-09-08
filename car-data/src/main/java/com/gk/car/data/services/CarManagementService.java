package com.gk.car.data.services;

import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.data.dto.AddCarDto;

public interface CarManagementService {

  void addCar(AddCarDto addCarDto);

  void updateSimilarCars(CarSimilarityUpdateDto carSimilarityUpdateDto);
}
