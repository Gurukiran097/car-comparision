package com.gk.car.data.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CarSimilarityDto implements Serializable {

  private String carVariantId;

  private List<String> variants;
}
