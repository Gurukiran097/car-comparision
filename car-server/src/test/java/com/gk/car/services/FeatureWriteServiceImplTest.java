package com.gk.car.services;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.commons.entities.FeatureEntity;
import com.gk.car.commons.enums.FeatureType;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.FeatureRepository;
import com.gk.car.data.services.impl.FeatureWriteServiceImpl;
import com.gk.car.data.utils.IdUtil;
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
  void addFeature_withNullFeatureName_throwsException() {
    AddFeatureDto addFeatureDto = new AddFeatureDto();
    addFeatureDto.setFeatureType(FeatureType.CLASSIFICATION);
    addFeatureDto.setFeatureKey("Key");
    addFeatureDto.setFeatureCategory("Category");

    assertThrows(GenericServiceException.class, () -> featureWriteService.addFeature(addFeatureDto));
  }
}
