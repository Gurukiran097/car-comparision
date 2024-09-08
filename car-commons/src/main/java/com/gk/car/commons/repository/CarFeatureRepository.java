package com.gk.car.commons.repository;

import com.gk.car.commons.entities.CarFeatureEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarFeatureRepository extends JpaRepository<CarFeatureEntity, Long> {

  List<CarFeatureEntity> findAllByCarVariantIdIn(List<String> carVariants);
}
