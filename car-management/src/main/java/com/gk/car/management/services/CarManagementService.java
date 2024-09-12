package com.gk.car.management.services;

import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.commons.dto.AddCarDto;

public interface CarManagementService {

  String addCar(AddCarDto addCarDto);

  String addVariant(AddCarVariantDto addCarVariantDto, String carId);

  void addCarFeature(AddCarFeatureDto addCarFeatureDto, String carVariantId);
}
