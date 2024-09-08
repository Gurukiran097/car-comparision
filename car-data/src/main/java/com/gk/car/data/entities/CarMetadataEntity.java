package com.gk.car.data.entities;

import com.gk.car.data.enums.CarType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "car_metadata"
)
public class CarMetadataEntity extends BaseEntity {

  @Column(name = "car_id")
  private String carId;
  @Column(name = "manufacturer")
  private String manufacturer;
  @Column(name = "car_type")
  @Enumerated(EnumType.STRING)
  private CarType carType;
  @Column(name = "car_name")
  private String carName;
}
