package com.gk.car.data.entities;


import com.gk.car.data.enums.FeatureType;
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
    name = "features"
)
public class FeatureEntity extends BaseEntity{

  @Column(name = "feature_id")
  private String featureId;

  @Column(name = "feature_name")
  private String featureName;

  @Column(name = "feature_type")
  private FeatureType featureType;

}
