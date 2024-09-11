package com.gk.car.similarity.strategies;

import com.gk.car.commons.entities.CarMetadataEntity;
import com.gk.car.commons.entities.CarVariantEntity;
import com.gk.car.commons.enums.CarType;
import com.gk.car.commons.repository.CarMetadataRepository;
import com.gk.car.commons.repository.CarVariantRepository;
import com.gk.car.similarity.dto.CarFilteringDto;
import com.gk.car.similarity.dto.CarFilteringItemDto;
import com.gk.car.similarity.strategies.impl.CarTypeCarFilteringStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CarTypeCarFilteringStrategyTest {

    @Mock
    private CarMetadataRepository carMetadataRepository;

    @Mock
    private CarVariantRepository carVariantRepository;

    @InjectMocks
    private CarTypeCarFilteringStrategy carTypeCarFilteringStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void filterCars_withMultipleCarTypes_returnsFilteredCars() {
        CarMetadataEntity suvCar1 = new CarMetadataEntity();
        suvCar1.setCarId("1");
        CarMetadataEntity suvCar2 = new CarMetadataEntity();
        suvCar2.setCarId("2");

        CarMetadataEntity sedanCar1 = new CarMetadataEntity();
        sedanCar1.setCarId("3");
        CarMetadataEntity sedanCar2 = new CarMetadataEntity();
        sedanCar2.setCarId("4");

        CarVariantEntity suvVariant1 = new CarVariantEntity();
        suvVariant1.setVariantId("1");
        CarVariantEntity suvVariant2 = new CarVariantEntity();
        suvVariant2.setVariantId("2");

        CarVariantEntity sedanVariant1 = new CarVariantEntity();
        sedanVariant1.setVariantId("3");
        CarVariantEntity sedanVariant2 = new CarVariantEntity();
        sedanVariant2.setVariantId("4");

        when(carMetadataRepository.findAllByCarType(CarType.SUV)).thenReturn(List.of(suvCar1, suvCar2));
        when(carMetadataRepository.findAllByCarType(CarType.SEDAN)).thenReturn(List.of(sedanCar1, sedanCar2));
        when(carVariantRepository.findAllByCarIdIn(List.of("1", "2"))).thenReturn(List.of(suvVariant1, suvVariant2));
        when(carVariantRepository.findAllByCarIdIn(List.of("3", "4"))).thenReturn(List.of(sedanVariant1, sedanVariant2));

        CarFilteringDto result = carTypeCarFilteringStrategy.filterCars();

        assertEquals(2, result.getCars().size());
        assertEquals("1", result.getCars().get(0).get(0).getCarVariantId());
        assertEquals("2", result.getCars().get(0).get(1).getCarVariantId());
        assertEquals("3", result.getCars().get(1).get(0).getCarVariantId());
        assertEquals("4", result.getCars().get(1).get(1).getCarVariantId());
    }

    @Test
    void filterCars_withNoMatchingCars_returnsEmptyList() {
        when(carMetadataRepository.findAllByCarType(CarType.SUV)).thenReturn(List.of());

        CarFilteringDto result = carTypeCarFilteringStrategy.filterCars();

        assertEquals(0, result.getCars().size());
    }

    @Test
    void filterCars_withNoMatchingVariants_returnsEmptyList() {
        CarMetadataEntity carMetadataEntity = new CarMetadataEntity();
        carMetadataEntity.setCarId("1");

        when(carMetadataRepository.findAllByCarType(CarType.SUV)).thenReturn(List.of(carMetadataEntity));
        when(carVariantRepository.findAllByCarIdIn(List.of("1"))).thenReturn(List.of());

        CarFilteringDto result = carTypeCarFilteringStrategy.filterCars();

        assertEquals(0, result.getCars().size());
    }
}