package com.gk.car.data.dto;

import com.gk.car.commons.enums.CarType;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CarVariantDto implements Serializable {

  private String variantName;
  private String carName;
  private String imageUrl;
  private String manufacturer;
  private String variantId;
  private CarType carType;
  private List<CarFeatureDto> features;
}
