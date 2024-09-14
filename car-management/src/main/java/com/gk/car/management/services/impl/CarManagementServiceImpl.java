package com.gk.car.management.services.impl;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.management.clients.CarDataClient;
import com.gk.car.management.services.CarManagementService;
import feign.FeignException;
import feign.FeignException.FeignClientException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarManagementServiceImpl implements CarManagementService {

  private final CarDataClient carDataClient;

  @Override
  public String addCar(AddCarDto addCarDto) {
    try{
      if( Objects.isNull(addCarDto) || addCarDto.getCarName() == null || addCarDto.getCarType() == null || addCarDto.getManufacturer() == null) {
        throw new GenericServiceException(ErrorCode.INVALID_DATA, "Car name, type and manufacturer are mandatory");
      }
      return carDataClient.addCar(addCarDto);
    }catch (FeignException e) {
      log.error("Exception while calling car data client", e);
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, e.contentUTF8(), HttpStatus.valueOf(e.status()));
    }
  }

  @Override
  public String addVariant(AddCarVariantDto addCarVariantDto, String carId) {
    try{
      if(Objects.isNull(addCarVariantDto) || addCarVariantDto.getVariantName() == null || addCarVariantDto.getImageUrl() == null || Objects.isNull(carId)) {
        throw new GenericServiceException(ErrorCode.INVALID_DATA, "Variant name and image mandatory");
      }
      return carDataClient.addCarVariant(carId, addCarVariantDto);
    }catch (FeignException e) {
      log.error("Exception while calling car data client", e);
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, e.contentUTF8(), HttpStatus.valueOf(e.status()));
    }
  }

  @Override
  public void addCarFeature(AddCarFeatureDto addCarFeatureDto, String carVariantId) {
    try{
      if(Objects.isNull(addCarFeatureDto) || addCarFeatureDto.getFeatureId() == null || Objects.isNull(carVariantId)) {
        throw new GenericServiceException(ErrorCode.INVALID_DATA, "Feature name and value are mandatory");
      }
      carDataClient.addCarFeature(carVariantId, addCarFeatureDto);
    }catch (FeignException e) {
      log.error("Exception while calling car data client", e);
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, e.contentUTF8(), HttpStatus.valueOf(e.status()));
    }
  }
}
