package com.gk.car.commons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    name = "car_variants",
    indexes = {
        @Index(name = "car_id_idx", columnList = "car_id"),
        @Index(name = "variant_id_idx", columnList = "variant_id"),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "car_id_udx", columnNames = {"car_id"})
    }
)
public class CarVariantEntity extends BaseEntity {

  @Column(name = "car_id")
  private String carId;
  @Column(name = "variant_id")
  private String variantId;
  @Column(name = "variant_name")
  private String variantName;
  @Column(name = "image_url")
  private String imageUrl;

}
