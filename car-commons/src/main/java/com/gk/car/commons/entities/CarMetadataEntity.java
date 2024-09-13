package com.gk.car.commons.entities;

import com.gk.car.commons.enums.CarType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
    name = "car_metadata",
    indexes = {
        @Index(name = "car_type_idx", columnList = "car_type")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "car_id_udx", columnNames = {"car_id"})
    }
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
