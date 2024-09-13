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
import com.gk.car.data.dto.CarFeatureDto;
import com.gk.car.data.dto.CarVariantDto;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
