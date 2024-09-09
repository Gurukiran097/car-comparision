package com.gk.car.commons.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddCarFeatureDto implements Serializable {

  private String featureId;
  private Integer featureValue;

}
