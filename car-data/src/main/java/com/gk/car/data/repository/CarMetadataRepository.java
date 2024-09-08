package com.gk.car.data.repository;

import com.gk.car.data.entities.CarMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMetadataRepository extends JpaRepository<CarMetadataEntity, Long> {
}
