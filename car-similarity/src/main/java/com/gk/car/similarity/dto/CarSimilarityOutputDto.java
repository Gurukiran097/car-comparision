package com.gk.car.similarity.dto;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarSimilarityOutputDto {

  private Map<String, List<CarSimilarityOutputItemDto>> similarityMap;
}
