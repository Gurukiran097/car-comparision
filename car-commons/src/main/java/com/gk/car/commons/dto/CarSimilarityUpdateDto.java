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
public class CarSimilarityUpdateDto implements Serializable {

  private String carVariantId;

  private List<String> similarVariants;
}
