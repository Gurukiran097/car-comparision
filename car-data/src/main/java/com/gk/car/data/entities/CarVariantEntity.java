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
    name = "car_variants"
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
