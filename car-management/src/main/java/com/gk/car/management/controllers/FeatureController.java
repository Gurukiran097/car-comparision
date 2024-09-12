package com.gk.car.management.controllers;

import static com.gk.car.management.constants.SecurityConstants.OPEN_API_SCHEME;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.management.services.FeatureService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/management/feature")
@RequiredArgsConstructor
public class FeatureController {

  private final FeatureService featureService;

  @PostMapping(value = "/")
  @SecurityRequirement(name = OPEN_API_SCHEME)
  public String addFeature(@RequestBody AddFeatureDto addFeatureDto) {
    log.info("Called with {}", addFeatureDto);
    return featureService.addFeature(addFeatureDto);
  }


}
