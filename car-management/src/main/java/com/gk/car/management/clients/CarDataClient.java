package com.gk.car.management.clients;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.commons.dto.AddFeatureDto;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface CarDataClient {

  @RequestLine("POST /v1/api/write/car/")
  @Headers({
      "Content-type: application/json",
      "Accept: */*",
  })
  String addCar(AddCarDto addCarDto);

  @RequestLine("POST /v1/api/write/car/variant/{carId}")
  @Headers({
      "Content-type: application/json",
      "Accept: */*",
  })
  String addCarVariant(@Param("carId") String carId,  AddCarVariantDto addCarVariantDto);

  @RequestLine("POST /v1/api/write/car/feature/{carVariantId}")
  @Headers({
      "Content-type: application/json",
      "Accept: */*",
  })
  void addCarFeature(@Param("carVariantId") String carVariantId, AddCarFeatureDto addCarFeatureDto);

  @RequestLine("POST /v1/api/write/feature/")
  @Headers({
      "Content-type: application/json",
      "Accept: */*",
  })
  String addFeature(AddFeatureDto addFeatureDto);
}
