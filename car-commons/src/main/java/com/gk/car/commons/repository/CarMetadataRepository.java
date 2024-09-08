package com.gk.car.commons.repository;

import com.gk.car.commons.entities.CarMetadataEntity;
import com.gk.car.commons.enums.CarType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMetadataRepository extends JpaRepository<CarMetadataEntity, Long> {

  List<CarMetadataEntity> findAllByCarType(CarType carType);
}
