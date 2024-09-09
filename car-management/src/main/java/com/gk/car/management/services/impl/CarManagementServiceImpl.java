package com.gk.car.management.services.impl;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.management.clients.CarDataClient;
import com.gk.car.management.services.CarManagementService;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarManagementServiceImpl implements CarManagementService {

  private final CarDataClient carDataClient;

  @Override
  public void addCar(AddCarDto addCarDto) {
    try{
      carDataClient.addCar(addCarDto);
    }catch (FeignClientException e) {
      log.error("Exception while calling car data client", e);
      log.error("Exception inside ", e.getCause());
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
    }
  }

  @Override
  public void addVariant(AddCarVariantDto addCarVariantDto, String carId) {
    try{
      carDataClient.addCarVariant(carId, addCarVariantDto);
    }catch (FeignClientException e) {
      log.error("Exception while calling car data client", e);
      log.error("Exception inside ", e.getCause());
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
    }
  }

  @Override
  public void addCarFeature(AddCarFeatureDto addCarFeatureDto, String carVariantId) {
    try{
      carDataClient.addCarFeature(carVariantId, addCarFeatureDto);
    }catch (FeignClientException e) {
      log.error("Exception while calling car data client", e);
      log.error("Exception inside ", e.getCause());
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
    }
  }
}
