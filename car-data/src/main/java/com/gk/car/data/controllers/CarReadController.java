package com.gk.car.data.controllers;

import com.gk.car.data.dto.CarSimilarityDto;
import com.gk.car.data.dto.CarVariantDto;
import com.gk.car.data.dto.CarVariantListDto;
import com.gk.car.data.services.CarReadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/car")
@RequiredArgsConstructor
public class CarReadController {

  private final CarReadService carReadService;

  @GetMapping("/{carVariantId}")
  public CarVariantDto getCar(@PathVariable String carVariantId) {
    return carReadService.getCar(carVariantId);
  }

  @PostMapping("/compare")
  public CarVariantListDto getCar(@RequestBody List<String> variants) {
    return carReadService.getCars(variants);
  }

  @PostMapping("/compare/differences")
  public CarVariantListDto getCarDifferences(@RequestBody List<String> variants) {
    return carReadService.getCarDifferences(variants);
  }

  @GetMapping("/similar/{carVariantId}")
  public CarSimilarityDto getCarDifferences(@PathVariable String carVariantId) {
    return carReadService.getSimilarCars(carVariantId);
  }

}
