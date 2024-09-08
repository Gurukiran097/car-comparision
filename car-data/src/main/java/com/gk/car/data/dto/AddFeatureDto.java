package com.gk.car.data.dto;

import com.gk.car.data.enums.FeatureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddFeatureDto {

  private String featureName;

  private FeatureType featureType;

}
