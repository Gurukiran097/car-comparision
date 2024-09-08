package com.gk.car.data.repository;

import com.gk.car.data.entities.CarMetadataEntity;
import com.gk.car.data.entities.CarVariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarVariantRepository extends JpaRepository<CarVariantEntity, Long> {
}
