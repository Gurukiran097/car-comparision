package com.gk.car.commons.dto;

import com.gk.car.commons.enums.FeatureType;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddFeatureDto implements Serializable {

  private String featureName;

  private FeatureType featureType;

  private String featureKey;

  private String featureCategory;

}
