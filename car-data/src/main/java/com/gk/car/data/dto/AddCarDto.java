package com.gk.car.data.dto;

import com.gk.car.commons.enums.CarType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddCarDto {

  private String carName;
  private String manufacturer;
  private CarType carType;
  private List<AddCarVariantDto> variants;

}
