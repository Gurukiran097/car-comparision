package com.gk.car.commons.repository;

import com.gk.car.commons.entities.CarVariantEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarVariantRepository extends JpaRepository<CarVariantEntity, Long> {

  List<CarVariantEntity> findAllByCarIdIn(List<String> cars);

  Optional<CarVariantEntity> findByVariantId(String variantId);
}
