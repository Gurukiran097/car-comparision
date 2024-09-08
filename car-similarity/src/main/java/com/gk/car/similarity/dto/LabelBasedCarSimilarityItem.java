package com.gk.car.similarity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelBasedCarSimilarityItem {

  private String carVariantId;
  private int score;
}
