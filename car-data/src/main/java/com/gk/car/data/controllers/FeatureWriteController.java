package com.gk.car.data.controllers;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.data.services.FeatureWriteService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/write/feature")
@RequiredArgsConstructor
@Hidden
public class FeatureWriteController {

  private final FeatureWriteService featureWriteService;

  @PostMapping(value = "/")
  private void addFeature(@RequestBody AddFeatureDto addFeatureDto) {
    featureWriteService.addFeature(addFeatureDto);
  }


}
