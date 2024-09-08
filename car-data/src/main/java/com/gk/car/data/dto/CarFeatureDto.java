package com.gk.car.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CarFeatureDto {

  private String featureId;
  private Integer featureValue;

}
