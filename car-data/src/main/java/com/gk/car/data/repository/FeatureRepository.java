package com.gk.car.data.repository;

import com.gk.car.data.entities.CarMetadataEntity;
import com.gk.car.data.entities.FeatureEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<FeatureEntity, Long> {

  Optional<FeatureEntity> findByFeatureId(String featureId);
}
