package com.gk.car.data.test.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gk.car.commons.entities.*;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.*;
import com.gk.car.data.dto.CarFeatureDto;
import com.gk.car.data.dto.CarVariantDto;
import com.gk.car.data.utils.CarUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.*;

class CarUtilsTest {

    @Mock
    private CarMetadataRepository carMetadataRepository;

    @Mock
    private CarVariantRepository carVariantRepository;

    @Mock
    private CarFeatureRepository carFeatureRepository;

    @Mock
    private FeatureRepository featureRepository;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private CarUtils carUtils;

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

        carUtils.getCarFromDatabase("1");
    }

    @Test
    void getCar_withInvalidId_throwsException() {
        when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.empty());

        assertThrows(GenericServiceException.class, () -> carUtils.getCarFromDatabase("invalid"));
    }

    @Test
    void getCar_withInvalidCar_throwsException() {
        when(carVariantRepository.findByVariantId(anyString())).thenReturn(Optional.of(new CarVariantEntity()));
        when(carMetadataRepository.findByCarId(anyString())).thenReturn(Optional.empty());

        assertThrows(GenericServiceException.class, () -> carUtils.getCarFromDatabase("invalid"));
    }
}