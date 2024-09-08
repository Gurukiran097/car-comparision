package com.gk.car.data.controllers;

import com.gk.car.data.dto.AddCarDto;
import com.gk.car.data.services.CarManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/car/")
@RequiredArgsConstructor
public class CarController {

  private final CarManagementService carManagementService;

  @PostMapping(value = "/add")
  public void addCar(@RequestBody AddCarDto addCarDto) {
    carManagementService.addCar(addCarDto);
  }

  @GetMapping(value = "/health")
  public String health() {
    return "isAlive";
  }

}
