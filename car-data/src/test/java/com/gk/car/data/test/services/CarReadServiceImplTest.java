package com.gk.car.data.test.services;

import com.gk.car.commons.entities.CarFeatureEntity;
import com.gk.car.commons.entities.CarMetadataEntity;
import com.gk.car.commons.entities.CarVariantEntity;
import com.gk.car.commons.entities.FeatureEntity;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.CarFeatureRepository;
import com.gk.car.commons.repository.CarMetadataRepository;
import com.gk.car.commons.repository.CarVariantRepository;
import com.gk.car.commons.repository.FeatureRepository;
import com.gk.car.data.repository.RedisRepository;
import com.gk.car.data.services.impl.CarReadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CarReadServiceImplTest {

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
  private CarReadServiceImpl carReadService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getCar_returnsCarVariantDto() {
    CarVariantEntity carVariantEntity = new CarVariantEntity();
    CarMetadataEntity carMetadataEntity = new CarMetadataEntity();
    carVariantEntity.setCarId("1");
    when(carMetadataRepository.findByCarId(anyString())).thenReturn(Optional.of(carMetadataEntity));
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.of(carVariantEntity));
    when(carFeatureRepository.findAllByCarVariantId(anyString())).thenReturn(List.of(new CarFeatureEntity()));
    when(featureRepository.findAllByFeatureIdIn(anyList())).thenReturn(List.of(new FeatureEntity()));

    carReadService.getCar("1");
  }

  @Test
  void getCar_withInvalidId_throwsException() {
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.empty());

    assertThrows(GenericServiceException.class, () -> carReadService.getCar("invalid"));
  }

  @Test
  void getCar_withInvalidCar_throwsException() {
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.of(new CarVariantEntity()));
    when(carMetadataRepository.findByCarId(anyString())).thenReturn(Optional.empty());

    assertThrows(GenericServiceException.class, () -> carReadService.getCar("invalid"));
  }

  @Test
  void getCars_returnsCarVariantListDto() {
    CarVariantEntity carVariantEntity = new CarVariantEntity();
    carVariantEntity.setCarId("1");
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.of(carVariantEntity));
    when(carMetadataRepository.findByCarId(anyString())).thenReturn(Optional.of(new CarMetadataEntity()));
    when(carFeatureRepository.findAllByCarVariantId(anyString())).thenReturn(List.of(new CarFeatureEntity()));
    when(featureRepository.findAllByFeatureIdIn(anyList())).thenReturn(List.of(new FeatureEntity()));

    carReadService.getCars(List.of("1", "2"));
  }

  @Test
  void getCars_withInvalidList_throwsException() {
    assertThrows(GenericServiceException.class, () -> carReadService.getCars(List.of("1")));
  }

  @Test
  void getCars_withInvalidListGreater_throwsException() {
    assertThrows(GenericServiceException.class, () -> carReadService.getCars(List.of("1","2","3","4")));
  }

  @Test
  void getCarDifferences_returnsCarVariantListDto() {
    CarVariantEntity carVariantEntity = new CarVariantEntity();
    carVariantEntity.setCarId("1");
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.of(carVariantEntity));
    when(carMetadataRepository.findByCarId(anyString())).thenReturn(Optional.of(new CarMetadataEntity()));
    when(carFeatureRepository.findAllByCarVariantId(anyString())).thenReturn(List.of(new CarFeatureEntity()));
    when(featureRepository.findAllByFeatureIdIn(anyList())).thenReturn(List.of(new FeatureEntity()));

    carReadService.getCarDifferences(List.of("1", "2"));
  }

  @Test
  void getCarDifferences_withInvalidList_throwsException() {
    assertThrows(GenericServiceException.class, () -> carReadService.getCarDifferences(List.of("1")));
  }

  @Test
  void getCarDifferences_withListNull_throwsException() {
    assertThrows(GenericServiceException.class, () -> carReadService.getCarDifferences(null));
  }

  @Test
  void getCarDifferences_withListSmaller_throwsException() {
    assertThrows(GenericServiceException.class, () -> carReadService.getCarDifferences(List.of()));
  }

  @Test
  void getCarDifferences_withInvalidListGreater_throwsException() {
    assertThrows(GenericServiceException.class, () -> carReadService.getCarDifferences(List.of("1","2","3","4")));
  }

  @Test
  void getSimilarCars_returnsCarSimilarityDto() {
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.of(new CarVariantEntity()));
    when(redisRepository.fetchList(anyString())).thenReturn(List.of("2", "3"));

    carReadService.getSimilarCars("1");
  }

  @Test
  void getSimilarCars_withInvalidId_throwsException() {
    when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.empty());

    assertThrows(GenericServiceException.class, () -> carReadService.getSimilarCars("invalid"));
  }
}
