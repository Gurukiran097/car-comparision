package com.gk.car.commons.repository;

import com.gk.car.commons.entities.FeatureEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<FeatureEntity, Long> {

  Optional<FeatureEntity> findByFeatureId(String featureId);

  List<FeatureEntity> findAllByFeatureIdIn(List<String> featureIds);
}
