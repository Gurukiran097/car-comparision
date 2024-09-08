package com.gk.car.data.dto;

import com.gk.car.commons.enums.FeatureType;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class CarFeatureDto implements Serializable {

  private FeatureType featureType;
  private String featureName;
  private Integer featureValue;
  private String featureId;
}
