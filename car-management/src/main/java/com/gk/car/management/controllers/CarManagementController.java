package com.gk.car.management.controllers;

import static com.gk.car.management.constants.SecurityConstants.OPEN_API_SCHEME;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.management.services.CarManagementService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/management/car")
@RequiredArgsConstructor
public class CarManagementController {

  private final CarManagementService carManagementService;

  @PostMapping(value = "/")
  @SecurityRequirement(name = OPEN_API_SCHEME)
  public void addCar(@RequestBody AddCarDto addCarDto) {
    carManagementService.addCar(addCarDto);
  }

  @PostMapping(value = "/variant/{carId}")
  @SecurityRequirement(name = OPEN_API_SCHEME)
  public void addCarVariant(@RequestBody AddCarVariantDto addCarVariantDto, @PathVariable String carId) {
    carManagementService.addVariant(addCarVariantDto, carId);
  }

  @PostMapping(value = "/feature/{carVariantId}")
  @SecurityRequirement(name = OPEN_API_SCHEME)
  public void addCarFeature(@RequestBody AddCarFeatureDto carFeatureDto, @PathVariable String carVariantId) {
    carManagementService.addCarFeature(carFeatureDto, carVariantId);
  }



}
