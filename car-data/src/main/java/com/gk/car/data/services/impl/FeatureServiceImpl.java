package com.gk.car.data.services.impl;

import com.gk.car.data.dto.AddFeatureDto;
import com.gk.car.data.entities.FeatureEntity;
import com.gk.car.data.repository.FeatureRepository;
import com.gk.car.data.services.FeatureService;
import com.gk.car.data.utils.IdUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {

  private final FeatureRepository featureRepository;


  @Override
  public String addFeature(AddFeatureDto addFeatureDto) {
    FeatureEntity feature = FeatureEntity.builder()
        .featureId(IdUtil.generateUUID())
        .featureName(addFeatureDto.getFeatureName())
        .featureType(addFeatureDto.getFeatureType())
        .build();
    featureRepository.save(feature);
    return feature.getFeatureId();
  }
}
