package com.gk.car.similarity.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarFilteringDto {
  List<List<CarFilteringItemDto>> cars;
}
