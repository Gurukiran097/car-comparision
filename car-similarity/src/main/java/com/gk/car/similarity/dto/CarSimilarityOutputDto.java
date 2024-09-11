package com.gk.car.similarity.dto;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarSimilarityOutputDto {

  private Map<String, List<CarSimilarityOutputItemDto>> similarityMap;
}
