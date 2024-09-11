package com.gk.car.commons.dto;

import com.gk.car.commons.enums.CarType;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCarDto implements Serializable {

  private String carName;
  private String manufacturer;
  private CarType carType;
  private List<AddCarVariantDto> variants;

}
