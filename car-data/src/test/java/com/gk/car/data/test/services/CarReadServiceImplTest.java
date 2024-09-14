package com.gk.car.data.test.services;

import com.gk.car.commons.entities.CarVariantEntity;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.CarFeatureRepository;
import com.gk.car.commons.repository.CarMetadataRepository;
import com.gk.car.commons.repository.CarVariantRepository;
import com.gk.car.commons.repository.FeatureRepository;
import com.gk.car.data.dto.CarFeatureDto;
import com.gk.car.data.dto.CarVariantDto;
import com.gk.car.data.dto.CarVariantListDto;
import com.gk.car.data.repository.RedisRepository;
import com.gk.car.data.services.impl.CarReadServiceImpl;
import com.gk.car.data.utils.CarUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
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

  @Mock
  private CarUtils carUtils;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getCar_callsCarUtils() {
    String carVariantId = "variant123";
    CarVariantDto carVariantDto = new CarVariantDto();

    when(carUtils.getCarFromDatabase(carVariantId)).thenReturn(carVariantDto);

    carReadService.getCar(carVariantId);

    verify(carUtils).getCarFromDatabase(carVariantId);
  }



  @Test
  void getCars_returnsCarVariantListDto() {
    when(carUtils.getCarFromDatabase(anyString())).thenReturn(new CarVariantDto());

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
    CarVariantDto carVariantDto = new CarVariantDto();
    carVariantDto.setFeatures(List.of(new CarFeatureDto()));
    when(carUtils.getCarFromDatabase(anyString())).thenReturn(carVariantDto);

    carReadService.getCarDifferences(List.of("1", "2"));
  }

  @Test
  void getCarDifferences_SimilarFeaturesSkipped_DifferentFeaturesSelected_WithThreeCars() {
    String carVariantId1 = "variant1";
    String carVariantId2 = "variant2";
    String carVariantId3 = "variant3";

    CarVariantDto carVariantDto1 = CarVariantDto.builder()
        .variantId(carVariantId1)
        .features(List.of(
            CarFeatureDto.builder().featureId("feature1").featureName("Feature 1").build(),
            CarFeatureDto.builder().featureId("feature2").featureName("Feature 2").build()
        ))
        .build();

    CarVariantDto carVariantDto2 = CarVariantDto.builder()
        .variantId(carVariantId2)
        .features(List.of(
            CarFeatureDto.builder().featureId("feature1").featureName("Feature 1").build(),
            CarFeatureDto.builder().featureId("feature3").featureName("Feature 3").build()
        ))
        .build();

    CarVariantDto carVariantDto3 = CarVariantDto.builder()
        .variantId(carVariantId3)
        .features(List.of(
            CarFeatureDto.builder().featureId("feature2").featureName("Feature 2").build(),
            CarFeatureDto.builder().featureId("feature4").featureName("Feature 4").build()
        ))
        .build();

    when(carReadService.getCar(carVariantId1)).thenReturn(carVariantDto1);
    when(carReadService.getCar(carVariantId2)).thenReturn(carVariantDto2);
    when(carReadService.getCar(carVariantId3)).thenReturn(carVariantDto3);

    CarVariantListDto result = carReadService.getCarDifferences(List.of(carVariantId1, carVariantId2, carVariantId3));

    assertNotNull(result);
    assertEquals(3, result.getCars().size());

    CarVariantDto resultVariant1 = result.getCars().get(0);
    CarVariantDto resultVariant2 = result.getCars().get(1);
    CarVariantDto resultVariant3 = result.getCars().get(2);

    assertEquals(0, resultVariant1.getFeatures().size());

    assertEquals(1, resultVariant2.getFeatures().size());
    assertEquals("Feature 3", resultVariant2.getFeatures().get(0).getFeatureName());

    assertEquals(1, resultVariant3.getFeatures().size());
    assertEquals("Feature 4", resultVariant3.getFeatures().get(0).getFeatureName());
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
