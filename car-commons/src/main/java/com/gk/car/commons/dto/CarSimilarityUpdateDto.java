package com.gk.car.commons.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarSimilarityUpdateDto {

  private String carVariantId;

  private List<String> similarVariants;
}
