package com.gk.car.commons.entities;

import com.gk.car.commons.enums.FeatureType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
    name = "car_feature",
    indexes = {
        @Index(name = "car_variant_id_idx", columnList = "car_variant_id"),
        @Index(name = "feature_id_idx", columnList = "feature_id"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "car_id_feature_id_udx", columnNames = {"car_variant_id", "feature_id"})
    }
)
public class CarFeatureEntity extends BaseEntity {

  @Column(name = "car_variant_id")
  private String carVariantId;

  @Column(name = "feature_id")
  private String featureId;

  @Column(name = "feature_type")
  @Enumerated(EnumType.STRING)
  private FeatureType featureType;

  @Column(name = "feature_value")
  private Integer featureValue;
}
