package com.gk.car.data.controllers;

import com.gk.car.data.dto.AddFeatureDto;
import com.gk.car.data.services.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/feature")
@RequiredArgsConstructor
public class FeatureController {

  private final FeatureService featureService;

  @PostMapping(value = "/add")
  private String addFeature(AddFeatureDto addFeatureDto) {
    return featureService.addFeature(addFeatureDto);
  }


}
