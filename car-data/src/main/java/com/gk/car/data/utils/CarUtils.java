package com.gk.car.data.utils;

import com.gk.car.commons.entities.CarFeatureEntity;
import com.gk.car.commons.entities.CarMetadataEntity;
import com.gk.car.commons.entities.CarVariantEntity;
import com.gk.car.commons.entities.FeatureEntity;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.CarFeatureRepository;
import com.gk.car.commons.repository.CarMetadataRepository;
import com.gk.car.commons.repository.CarVariantRepository;
import com.gk.car.commons.repository.FeatureRepository;
import com.gk.car.data.dto.CarFeatureDto;
import com.gk.car.data.dto.CarVariantDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CarUtils {

  private final CarMetadataRepository carMetadataRepository;

  private final CarVariantRepository carVariantRepository;

  private final CarFeatureRepository carFeatureRepository;

  private final FeatureRepository featureRepository;

  @Cacheable(value = "carCache", key = "#carVariantId", cacheManager = "twoLayerCacheManager")
  public CarVariantDto getCarFromDatabase(String carVariantId) {
    log.info("Cache miss, calling database for car variant {}", carVariantId);
    Optional<CarVariantEntity> variantEntityOptional = carVariantRepository.findByVariantId(carVariantId);
    if(variantEntityOptional.isEmpty()) {
      throw new GenericServiceException(ErrorCode.INVALID_CAR_VARIANT_ID);
    }
    log.info("Car variant entity {}", variantEntityOptional);
    CarVariantEntity carVariant = variantEntityOptional.get();
    Optional<CarMetadataEntity> carMetadataEntityOptional = carMetadataRepository.findByCarId(carVariant.getCarId());
    log.info("Car metadata entity {}", carMetadataEntityOptional);
    if(carMetadataEntityOptional.isEmpty()) {
      throw new GenericServiceException(ErrorCode.INVALID_CAR_VARIANT_ID);
    }
    CarMetadataEntity carMetadataEntity = carMetadataEntityOptional.get();
    List<CarFeatureEntity> carFeatures = carFeatureRepository.findAllByCarVariantId(carVariantId);
    List<FeatureEntity> featureEntities = featureRepository.findAllByFeatureIdIn(carFeatures.stream().map(CarFeatureEntity::getFeatureId).toList());
    Map<String, FeatureEntity> featureMap = new HashMap<>();
    for(FeatureEntity featureEntity : featureEntities) {
      featureMap.put(featureEntity.getFeatureId(), featureEntity);
    }
    List<CarFeatureDto> carFeatureDtoList = new ArrayList<>();
    for(CarFeatureEntity carFeature : carFeatures) {
      FeatureEntity feature = featureMap.get(carFeature.getFeatureId());
      carFeatureDtoList.add(CarFeatureDto.builder()
          .featureName(feature.getFeatureName())
          .featureType(feature.getFeatureType())
          .featureId(feature.getFeatureId())
          .featureValue(carFeature.getFeatureValue())
          .featureKey(feature.getFeatureKey())
          .featureCategory(feature.getFeatureCategory())
          .build());
    }
    return CarVariantDto.builder()
        .carName(carMetadataEntity.getCarName())
        .carType(carMetadataEntity.getCarType())
        .variantName(carVariant.getVariantName())
        .variantId(carVariantId)
        .manufacturer(carMetadataEntity.getManufacturer())
        .imageUrl(carVariant.getImageUrl())
        .features(carFeatureDtoList)
        .build();
  }
}
