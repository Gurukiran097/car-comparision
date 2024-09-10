package com.gk.car.services;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.commons.dto.CarSimilarityUpdateDto;
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
import com.gk.car.data.services.impl.CarWriteServiceImpl;
import com.gk.car.data.utils.IdUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CarWriteServiceImplTest {

  @Mock
  private CarMetadataRepository carMetadataRepository;

  @Mock
  private CarVariantRepository carVariantRepository;

  @Mock
  private CarFeatureRepository carFeatureRepository;

  @Mock
  private FeatureRepository featureRepository;

  @Mock
  private RedisRepository redisRepository;

  @InjectMocks
  private CarWriteServiceImpl carWriteService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addCar_returnsCarId() {
    when(carMetadataRepository.save(any(CarMetadataEntity.class))).thenReturn(new CarMetadataEntity());
    when(carVariantRepository.saveAll(any())).thenReturn(null);
    when(carFeatureRepository.saveAll(any())).thenReturn(null);

    AddCarDto addCarDto = new AddCarDto();
    carWriteService.addCar(addCarDto);
  }

  @Test
  void addCar_withInvalidFeature_throwsException() {
    when(featureRepository.findByFeatureId(anyString())).thenReturn(Optional.empty());

    AddCarDto addCarDto = new AddCarDto();
    AddCarVariantDto addCarVariantDto = new AddCarVariantDto();
    addCarDto.setVariants(List.of(addCarVariantDto));
    AddCarFeatureDto addCarFeatureDto = new AddCarFeatureDto();
    addCarVariantDto.setFeatures(List.of(addCarFeatureDto));

    assertThrows(GenericServiceException.class, () -> carWriteService.addCar(addCarDto));
  }

  @Test
  void addVariant_returnsVariantId() {
    when(carMetadataRepository.findByCarId(anyString())).thenReturn(Optional.of(new CarMetadataEntity()));
    when(carVariantRepository.save(any(CarVariantEntity.class))).thenReturn(new CarVariantEntity());
    when(carFeatureRepository.saveAll(any())).thenReturn(null);

    AddCarVariantDto addCarVariantDto = new AddCarVariantDto();
    carWriteService.addVariant(addCarVariantDto, "1");
  }

  @Test
  void addVariant_withInvalidCarId_throwsException() {
    when(carMetadataRepository.findByCarId(anyString())).thenReturn(Optional.empty());

    AddCarVariantDto addCarVariantDto = new AddCarVariantDto();
    assertThrows(GenericServiceException.class, () -> carWriteService.addVariant(addCarVariantDto, "invalid"));
  }

  @Test
  void addCarFeature_returnsSuccess() {
    CarFeatureEntity carFeatureEntity = new CarFeatureEntity();
    carFeatureEntity.setFeatureId("1");
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.of(new CarVariantEntity()));
    when(featureRepository.findByFeatureId(anyString())).thenReturn(Optional.of(new FeatureEntity()));
    when(carFeatureRepository.save(any(CarFeatureEntity.class))).thenReturn(carFeatureEntity);

    AddCarFeatureDto addCarFeatureDto = new AddCarFeatureDto();
    addCarFeatureDto.setFeatureId("1");
    carWriteService.addCarFeature(addCarFeatureDto, "1");
  }

  @Test
  void addCarFeature_withInvalidFeature_throwsException() {
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.of(new CarVariantEntity()));
    when(featureRepository.findByFeatureId(anyString())).thenReturn(Optional.empty());

    AddCarFeatureDto addCarFeatureDto = new AddCarFeatureDto();
    assertThrows(GenericServiceException.class, () -> carWriteService.addCarFeature(addCarFeatureDto, "1"));
  }

  @Test
  void updateSimilarCars_returnsSuccess() {
    carWriteService.updateSimilarCars(new CarSimilarityUpdateDto());
  }
}
