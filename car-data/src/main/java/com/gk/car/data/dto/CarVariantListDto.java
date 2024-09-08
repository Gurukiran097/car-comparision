package com.gk.car.data.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarVariantListDto {

  private List<CarVariantDto> cars;
}
