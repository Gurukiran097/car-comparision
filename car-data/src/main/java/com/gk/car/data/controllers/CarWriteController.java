package com.gk.car.data.controllers;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.data.services.CarWriteService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/write/car")
@RequiredArgsConstructor
@Hidden
public class CarWriteController {

  private final CarWriteService carWriteService;

  @PostMapping(value = "/")
  public String addCar(@RequestBody AddCarDto addCarDto) {
    return carWriteService.addCar(addCarDto);
  }

  @PostMapping(value = "/variant/{carId}")
  public String addCarVariant(@RequestBody AddCarVariantDto addCarVariantDto, @PathVariable String carId) {
    return carWriteService.addVariant(addCarVariantDto, carId);
  }

  @PostMapping(value = "/feature/{carVariantId}")
  public void addCarFeature(@RequestBody AddCarFeatureDto carFeatureDto, @PathVariable String carVariantId) {
    carWriteService.addCarFeature(carFeatureDto, carVariantId);
  }



}
