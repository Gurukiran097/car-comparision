package com.gk.car.data.services.impl;

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
import com.gk.car.data.dto.CarSimilarityDto;
import com.gk.car.data.dto.CarVariantDto;
import com.gk.car.data.dto.CarVariantListDto;
import com.gk.car.data.repository.RedisRepository;
import com.gk.car.data.services.CarReadService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarReadServiceImpl implements CarReadService {

  private final CarMetadataRepository carMetadataRepository;

  private final CarVariantRepository carVariantRepository;

  private final CarFeatureRepository carFeatureRepository;

  private final FeatureRepository featureRepository;

  private final RedisRepository redisRepository;

  @Override
  public CarVariantDto getCar(String carVariantId) {
    Optional<CarVariantEntity> variantEntityOptional = carVariantRepository.findByVariantId(carVariantId);
    if(variantEntityOptional.isEmpty()) {
      throw new GenericServiceException(ErrorCode.INVALID_CAR_VARIANT_ID);
    }
    CarVariantEntity carVariant = variantEntityOptional.get();
    Optional<CarMetadataEntity> carMetadataEntityOptional = carMetadataRepository.findByCarId(carVariant.getCarId());
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

  @Override
  public CarVariantListDto getCars(List<String> carVariants) {
    verifyCarList(carVariants);
    List<CarVariantDto> variantDtos = new ArrayList<>();
    for(String car : carVariants) {
      variantDtos.add(getCar(car));
    }
    return CarVariantListDto.builder()
        .cars(variantDtos)
        .build();
  }

  @Override
  public CarVariantListDto getCarDifferences(List<String> carVariants) {
    verifyCarList(carVariants);
    List<CarVariantDto> variantDtos = new ArrayList<>();
    for(String car : carVariants) {
      variantDtos.add(getCar(car));
    }
    Set<CarFeatureDto> similarFeatures = new HashSet<>();
    Set<CarFeatureDto> differentFeatures = new HashSet<>();

    for(CarVariantDto carVariantDto : variantDtos) {
      for(CarFeatureDto featureDto : carVariantDto.getFeatures()) {
        if(!similarFeatures.contains(featureDto) && !differentFeatures.contains(featureDto)) {
          differentFeatures.add(featureDto);
        }else if(differentFeatures.contains(featureDto)) {
          differentFeatures.remove(featureDto);
          similarFeatures.add(featureDto);
        }
      }
    }

    for(CarVariantDto carVariantDto : variantDtos) {
      List<CarFeatureDto> differences = new ArrayList<>();
      for(CarFeatureDto featureDto : carVariantDto.getFeatures()) {
        if(differentFeatures.contains(featureDto)) differences.add(featureDto);
      }
      carVariantDto.setFeatures(differences);
    }

    return CarVariantListDto.builder()
        .cars(variantDtos)
        .build();
  }

  @Override
  public CarSimilarityDto getSimilarCars(String carVariantId) {
    Optional<CarVariantEntity> variantEntityOptional = carVariantRepository.findByVariantId(carVariantId);
    if(variantEntityOptional.isEmpty()) {
      throw new GenericServiceException(ErrorCode.INVALID_CAR_VARIANT_ID);
    }
    List<String> similarCars = redisRepository.fetchList(carVariantId);
    if(Objects.isNull(similarCars)) similarCars = new ArrayList<>();
    return CarSimilarityDto.builder()
        .carVariantId(carVariantId)
        .variants(similarCars)
        .build();
  }

  private void verifyCarList(List<String> carVariants) {
    if( Objects.isNull(carVariants) || carVariants.isEmpty() || carVariants.size()<2) {
      throw new GenericServiceException(ErrorCode.INVALID_CAR_VARIANT_SIZE_MIN);
    }
    if(carVariants.size() > 3) {
      throw new GenericServiceException(ErrorCode.INVALID_CAR_VARIANT_SIZE_MAX);
    }
  }

}
