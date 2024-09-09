package com.gk.car.commons.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarSimilarityUpdateDto implements Serializable {

  private String carVariantId;

  private List<String> similarVariants;
}
