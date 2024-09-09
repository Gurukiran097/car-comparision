package com.gk.car.data.services.impl;

import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.commons.entities.CarFeatureEntity;
import com.gk.car.commons.entities.CarMetadataEntity;
import com.gk.car.commons.entities.CarVariantEntity;
import com.gk.car.commons.entities.FeatureEntity;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.enums.FeatureType;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.CarFeatureRepository;
import com.gk.car.commons.repository.CarMetadataRepository;
import com.gk.car.commons.repository.CarVariantRepository;
import com.gk.car.commons.repository.FeatureRepository;
import com.gk.car.data.repository.RedisRepository;
import com.gk.car.data.services.CarWriteService;
import com.gk.car.data.utils.IdUtil;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CarWriteServiceImpl implements CarWriteService {

  private final CarMetadataRepository carMetadataRepository;

  private final CarVariantRepository carVariantRepository;

  private final CarFeatureRepository carFeatureRepository;

  private final FeatureRepository featureRepository;

  private final RedisRepository redisRepository;


  @Override
  @Transactional
  public String addCar(AddCarDto addCarDto) {
    log.info("Add car dto {}", addCarDto);
    CarMetadataEntity carMetadataEntity = CarMetadataEntity.builder()
        .carId(IdUtil.generateUUID())
        .carName(addCarDto.getCarName())
        .carType(addCarDto.getCarType())
        .manufacturer(addCarDto.getManufacturer())
        .build();
    List<CarVariantEntity> variants = new ArrayList<>();
    List<CarFeatureEntity> carFeatures = new ArrayList<>();
    if( Objects.nonNull(addCarDto.getVariants()) ) {
      for(AddCarVariantDto carVariantDto : addCarDto.getVariants()) {
        CarVariantEntity carVariantEntity = CarVariantEntity.builder()
            .carId(carMetadataEntity.getCarId())
            .variantName(carVariantDto.getVariantName())
            .variantId(IdUtil.generateUUID())
            .imageUrl(carVariantDto.getImageUrl())
            .build();
        variants.add(carVariantEntity);

        if(Objects.nonNull(carVariantDto.getFeatures())) {
          for(AddCarFeatureDto carFeatureDto : carVariantDto.getFeatures()) {
            Optional<FeatureEntity> featureEntityOptional = featureRepository.findByFeatureId(carFeatureDto.getFeatureId());
            if(featureEntityOptional.isEmpty()) {
              throw new GenericServiceException(ErrorCode.INVALID_FEATURE);
            }
            FeatureEntity feature = featureEntityOptional.get();
            CarFeatureEntity carFeatureEntity = CarFeatureEntity.builder()
                .carVariantId(carVariantEntity.getVariantId())
                .featureId(feature.getFeatureId())
                .featureType(feature.getFeatureType())
                .build();
            if(FeatureType.NUMERICAL.equals(feature.getFeatureType())) {
              if(Objects.isNull(carFeatureDto.getFeatureValue())) {
                throw new GenericServiceException(ErrorCode.INVALID_FEATURE_CONFIGURATION);
              }
              carFeatureEntity.setFeatureValue(carFeatureDto.getFeatureValue());
            }
            carFeatures.add(carFeatureEntity);
          }
        }
      }
    }
    carMetadataRepository.save(carMetadataEntity);
    carVariantRepository.saveAll(variants);
    carFeatureRepository.saveAll(carFeatures);
    return carMetadataEntity.getCarId();
  }

  @Override
  @Transactional
  public String addVariant(AddCarVariantDto carVariantDto, String carId) {
    Optional<CarMetadataEntity> carMetadataEntityOptional = carMetadataRepository.findByCarId(carId);
    if(carMetadataEntityOptional.isEmpty()) throw new GenericServiceException(ErrorCode.INVALID_CAR_ID);
    CarMetadataEntity carMetadataEntity = carMetadataEntityOptional.get();
    CarVariantEntity carVariantEntity = CarVariantEntity.builder()
        .carId(carMetadataEntity.getCarId())
        .variantName(carVariantDto.getVariantName())
        .variantId(IdUtil.generateUUID())
        .imageUrl(carVariantDto.getImageUrl())
        .build();
    List<CarFeatureEntity> carFeatures = new ArrayList<>();
    if(Objects.nonNull(carVariantDto.getFeatures())) {
      for(AddCarFeatureDto carFeatureDto : carVariantDto.getFeatures()) {
        Optional<FeatureEntity> featureEntityOptional = featureRepository.findByFeatureId(carFeatureDto.getFeatureId());
        if(featureEntityOptional.isEmpty()) {
          throw new GenericServiceException(ErrorCode.INVALID_FEATURE);
        }
        FeatureEntity feature = featureEntityOptional.get();
        CarFeatureEntity carFeatureEntity = CarFeatureEntity.builder()
            .carVariantId(carVariantEntity.getVariantId())
            .featureId(feature.getFeatureId())
            .featureType(feature.getFeatureType())
            .build();
        if(FeatureType.NUMERICAL.equals(feature.getFeatureType())) {
          if(Objects.isNull(carFeatureDto.getFeatureValue())) {
            throw new GenericServiceException(ErrorCode.INVALID_FEATURE_CONFIGURATION);
          }
          carFeatureEntity.setFeatureValue(carFeatureDto.getFeatureValue());
        }
        carFeatures.add(carFeatureEntity);
      }
    }

    carVariantRepository.save(carVariantEntity);
    carFeatureRepository.saveAll(carFeatures);
    return carVariantEntity.getVariantId();
  }

  @Override
  public void addCarFeature(AddCarFeatureDto carFeatureDto, String carVariantId) {
    Optional<CarVariantEntity> carVariantEntityOptional = carVariantRepository.findByVariantId(carVariantId);
    if(carVariantEntityOptional.isEmpty()) {
      throw new GenericServiceException(ErrorCode.INVALID_CAR_VARIANT_ID);
    }
    Optional<FeatureEntity> featureEntityOptional = featureRepository.findByFeatureId(carFeatureDto.getFeatureId());
    if(featureEntityOptional.isEmpty()) {
      throw new GenericServiceException(ErrorCode.INVALID_FEATURE);
    }
    FeatureEntity feature = featureEntityOptional.get();
    CarFeatureEntity carFeatureEntity = CarFeatureEntity.builder()
        .carVariantId(carVariantId)
        .featureId(feature.getFeatureId())
        .featureType(feature.getFeatureType())
        .build();
    if(FeatureType.NUMERICAL.equals(feature.getFeatureType())) {
      if(Objects.isNull(carFeatureDto.getFeatureValue())) {
        throw new GenericServiceException(ErrorCode.INVALID_FEATURE_CONFIGURATION);
      }
      carFeatureEntity.setFeatureValue(carFeatureDto.getFeatureValue());
    }
    carFeatureRepository.save(carFeatureEntity);
  }

  @Override
  public void updateSimilarCars(CarSimilarityUpdateDto carSimilarityUpdateDto) {
    redisRepository.clearAndSave(carSimilarityUpdateDto.getCarVariantId(), carSimilarityUpdateDto.getSimilarVariants());
  }
}
