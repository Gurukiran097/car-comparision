package com.gk.car.data.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddCarVariantDto {

  private String variantName;
  private String imageUrl;
  private List<AddCarFeatureDto> features;
}
