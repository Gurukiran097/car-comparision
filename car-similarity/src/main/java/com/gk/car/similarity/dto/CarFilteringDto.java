package com.gk.car.similarity.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarFilteringDto {
  List<List<CarFilteringItemDto>> cars;
}
