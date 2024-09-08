package com.gk.car.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    name = "car_feature"
)
public class CarFeatureEntity extends BaseEntity {

  @Column(name = "car_variant_id")
  private String carVariantId;

  @Column(name = "feature_id")
  private String featureId;

  @Column(name = "feature_value")
  private int featureValue;
}
