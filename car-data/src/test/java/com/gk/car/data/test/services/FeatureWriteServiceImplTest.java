package com.gk.car.data.test.services;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.commons.entities.FeatureEntity;
import com.gk.car.commons.enums.FeatureType;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.FeatureRepository;
import com.gk.car.data.services.impl.FeatureWriteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class FeatureWriteServiceImplTest {

  @Mock
  private FeatureRepository featureRepository;

  @InjectMocks
  private FeatureWriteServiceImpl featureWriteService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addFeature_savesFeature() {
    AddFeatureDto addFeatureDto = new AddFeatureDto();
    addFeatureDto.setFeatureName("Test Feature");
    addFeatureDto.setFeatureType(FeatureType.CLASSIFICATION);
    addFeatureDto.setFeatureKey("Key");
    addFeatureDto.setFeatureCategory("Category");

    featureWriteService.addFeature(addFeatureDto);

    verify(featureRepository).save(any(FeatureEntity.class));
  }

  @Test
  void addFeature_withNullFeature_throwsException() {
    assertThrows(GenericServiceException.class, () -> featureWriteService.addFeature(null));
  }

  @Test
  void addFeature_withNullFeatureKey_throwsException() {
    AddFeatureDto addFeatureDto = new AddFeatureDto();
    addFeatureDto.setFeatureType(FeatureType.CLASSIFICATION);
    addFeatureDto.setFeatureCategory("Category");
    addFeatureDto.setFeatureName("Test Feature");

    assertThrows(GenericServiceException.class, () -> featureWriteService.addFeature(addFeatureDto));
  }

  @Test
  void addFeature_withNullFeatureType_throwsException() {
    AddFeatureDto addFeatureDto = new AddFeatureDto();
    addFeatureDto.setFeatureName("Test Feature");
    addFeatureDto.setFeatureKey("Key");
    addFeatureDto.setFeatureCategory("Category");

    assertThrows(GenericServiceException.class, () -> featureWriteService.addFeature(addFeatureDto));
  }

  @Test
  void addFeature_withNullFeatureCategory_throwsException() {
    AddFeatureDto addFeatureDto = new AddFeatureDto();
    addFeatureDto.setFeatureName("Test Feature");
    addFeatureDto.setFeatureType(FeatureType.CLASSIFICATION);
    addFeatureDto.setFeatureKey("Key");

    assertThrows(GenericServiceException.class, () -> featureWriteService.addFeature(addFeatureDto));
  }

  @Test
  void addFeature_withNullFeatureName_throwsException() {
    AddFeatureDto addFeatureDto = new AddFeatureDto();
    addFeatureDto.setFeatureType(FeatureType.CLASSIFICATION);
    addFeatureDto.setFeatureKey("Key");
    addFeatureDto.setFeatureCategory("Category");

    assertThrows(GenericServiceException.class, () -> featureWriteService.addFeature(addFeatureDto));
  }
}
