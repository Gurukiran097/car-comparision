package com.gk.car.commons.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCarVariantDto implements Serializable {

  private String variantName;
  private String imageUrl;
  private List<AddCarFeatureDto> features;
}
