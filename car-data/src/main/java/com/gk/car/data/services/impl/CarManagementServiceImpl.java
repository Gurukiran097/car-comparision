package com.gk.car.data.services.impl;

import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.data.dto.AddCarDto;
import com.gk.car.data.dto.CarFeatureDto;
import com.gk.car.data.dto.CarVariantDto;
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
import com.gk.car.data.services.CarManagementService;
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
public class CarManagementServiceImpl implements CarManagementService {

  private final CarMetadataRepository carMetadataRepository;

  private final CarVariantRepository carVariantRepository;

  private final CarFeatureRepository carFeatureRepository;

  private final FeatureRepository featureRepository;

  private final RedisRepository redisRepository;


  @Override
  @Transactional
  public void addCar(AddCarDto addCarDto) {
    CarMetadataEntity carMetadataEntity = CarMetadataEntity.builder()
        .carId(IdUtil.generateUUID())
        .carName(addCarDto.getCarName())
        .carType(addCarDto.getCarType())
        .manufacturer(addCarDto.getManufacturer())
        .build();
    List<CarVariantEntity> variants = new ArrayList<>();
    List<CarFeatureEntity> carFeatures = new ArrayList<>();
    if( Objects.nonNull(addCarDto.getVariants()) ) {
      for(CarVariantDto carVariantDto : addCarDto.getVariants()) {
        CarVariantEntity carVariantEntity = CarVariantEntity.builder()
            .carId(carMetadataEntity.getCarId())
            .variantName(carVariantDto.getVariantName())
            .variantId(IdUtil.generateUUID())
            .imageUrl(carVariantDto.getImageUrl())
            .build();
        variants.add(carVariantEntity);

        if(Objects.nonNull(carVariantDto.getFeatures())) {
          for(CarFeatureDto carFeatureDto : carVariantDto.getFeatures()) {
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
  }

  @Override
  public void updateSimilarCars(CarSimilarityUpdateDto carSimilarityUpdateDto) {
    redisRepository.clearAndSave(carSimilarityUpdateDto.getCarVariantId(), carSimilarityUpdateDto.getSimilarVariants());
  }
}
