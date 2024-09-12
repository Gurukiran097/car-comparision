package com.gk.car.data.services.impl;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.commons.entities.FeatureEntity;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.FeatureRepository;
import com.gk.car.data.services.FeatureWriteService;
import com.gk.car.data.utils.IdUtil;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeatureWriteServiceImpl implements FeatureWriteService {

  private final FeatureRepository featureRepository;


  @Override
  public String addFeature(AddFeatureDto addFeatureDto) {
    log.info("Add car feature {}", addFeatureDto);
    if( Objects.isNull(addFeatureDto) || Objects.isNull(addFeatureDto.getFeatureName())
        || Objects.isNull(addFeatureDto.getFeatureType()) || Objects.isNull(addFeatureDto.getFeatureKey())
        || Objects.isNull(addFeatureDto.getFeatureCategory()) ) {
      throw new GenericServiceException(ErrorCode.INVALID_FEATURE, "Invalid feature details");
    }

    FeatureEntity feature = FeatureEntity.builder()
        .featureId(IdUtil.generateUUID())
        .featureName(addFeatureDto.getFeatureName())
        .featureType(addFeatureDto.getFeatureType())
        .featureKey(addFeatureDto.getFeatureKey())
        .featureCategory(addFeatureDto.getFeatureCategory())
        .build();
    featureRepository.save(feature);
    return feature.getFeatureId();
  }
}
