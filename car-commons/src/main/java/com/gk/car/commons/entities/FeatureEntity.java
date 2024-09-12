package com.gk.car.commons.entities;


import com.gk.car.commons.enums.FeatureType;
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
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "features"
)
public class FeatureEntity extends BaseEntity{

  @Column(name = "feature_id")
  @NonNull
  private String featureId;

  @Column(name = "feature_name")
  @NonNull
  private String featureName;

  @Column(name = "feature_key")
  @NonNull
  private String featureKey;

  @Column(name = "feature_category")
  @NonNull
  private String featureCategory;

  @Column(name = "feature_type")
  @NonNull
  @Enumerated(EnumType.STRING)
  private FeatureType featureType;

}
