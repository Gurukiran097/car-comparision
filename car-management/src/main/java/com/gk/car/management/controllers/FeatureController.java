package com.gk.car.management.controllers;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.management.services.FeatureService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/management/feature")
@RequiredArgsConstructor
public class FeatureController {

  private final FeatureService featureService;

  @PostMapping(value = "/")
  private void addFeature(AddFeatureDto addFeatureDto) {
    featureService.addFeature(addFeatureDto);
  }


}
