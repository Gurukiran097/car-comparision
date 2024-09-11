package com.gk.car.similarity.services;

import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.similarity.dto.*;
import com.gk.car.similarity.factories.CarFilteringStrategyFactory;
import com.gk.car.similarity.factories.CarSimilarityStrategyFactory;
import com.gk.car.similarity.services.impl.CarSimilarityServiceImpl;
import com.gk.car.similarity.strategies.CarFilteringStrategy;
import com.gk.car.similarity.strategies.CarSimilarityStrategy;
import com.gk.car.similarity.utils.KafkaPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CarSimilarityServiceImplTest {

    @Mock
    private CarFilteringStrategyFactory carFilteringStrategyFactory;

    @Mock
    private CarSimilarityStrategyFactory carSimilarityStrategyFactory;

    @Mock
    private KafkaPublisher kafkaPublisher;

    @Mock
    private CarFilteringStrategy carFilteringStrategy;

    @Mock
    private CarSimilarityStrategy carSimilarityStrategy;

    @InjectMocks
    private CarSimilarityServiceImpl carSimilarityServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(carFilteringStrategyFactory.getFilteringStrategy(anyString())).thenReturn(carFilteringStrategy);
        when(carSimilarityStrategyFactory.getSimilarityStrategy(anyString())).thenReturn(carSimilarityStrategy);
        ReflectionTestUtils.setField(carSimilarityServiceImpl, "filterStrategy", "CarTypeFilterStrategy");
        ReflectionTestUtils.setField(carSimilarityServiceImpl, "similarityStrategy", "LabelBasedSimilarityStrategy");
    }

    @Test
    void calculateSimilarities_withValidData_sendsKafkaMessages() {
        CarFilteringItemDto carFilteringItemDto = new CarFilteringItemDto("1");
        CarFilteringDto carFilteringDto = new CarFilteringDto(List.of(List.of(carFilteringItemDto)));
        when(carFilteringStrategy.filterCars()).thenReturn(carFilteringDto);

        CarSimilarityOutputItemDto carSimilarityOutputItemDto = new CarSimilarityOutputItemDto("2");
        CarSimilarityOutputDto carSimilarityOutputDto = new CarSimilarityOutputDto(Map.of("1", List.of(carSimilarityOutputItemDto)));
        when(carSimilarityStrategy.findSimilarCars(any(CarSimilarityInputDto.class))).thenReturn(carSimilarityOutputDto);

        carSimilarityServiceImpl.calculateSimilarities();

        verify(kafkaPublisher).send(eq("car-similarity-topic-v1"), any(CarSimilarityUpdateDto.class));
    }

    @Test
    void calculateSimilarities_withEmptyCarList_doesNotSendKafkaMessages() {
        CarFilteringDto carFilteringDto = new CarFilteringDto(List.of());
        when(carFilteringStrategy.filterCars()).thenReturn(carFilteringDto);

        carSimilarityServiceImpl.calculateSimilarities();

        verify(kafkaPublisher, never()).send(anyString(), any(CarSimilarityUpdateDto.class));
    }

    @Test
    void calculateSimilarities_withNullCarList_throwsException() {
        CarFilteringDto carFilteringDto = new CarFilteringDto(null);
        when(carFilteringStrategy.filterCars()).thenReturn(carFilteringDto);

        assertThrows(GenericServiceException.class, () -> carSimilarityServiceImpl.calculateSimilarities());
    }

    @Test
    void calculateSimilarities_withNullSimilarCars_throwsException() {
        CarSimilarityOutputDto carSimilarityOutputDto = new CarSimilarityOutputDto(null);
        CarFilteringItemDto carFilteringItemDto = new CarFilteringItemDto("1");
        CarFilteringDto carFilteringDto = new CarFilteringDto(List.of(List.of(carFilteringItemDto)));
        when(carFilteringStrategy.filterCars()).thenReturn(carFilteringDto);
        when(carSimilarityStrategy.findSimilarCars(any(CarSimilarityInputDto.class))).thenReturn(carSimilarityOutputDto);

        assertThrows(GenericServiceException.class, () -> carSimilarityServiceImpl.calculateSimilarities());
    }

    @Test
    void calculateSimilarities_withNullFilter_throwsException() {
        when(carFilteringStrategy.filterCars()).thenReturn(null);

        assertThrows(GenericServiceException.class, () -> carSimilarityServiceImpl.calculateSimilarities());
    }

    @Test
    void calculateSimilarities_withNullSimilarity_throwsException() {
        CarFilteringItemDto carFilteringItemDto = new CarFilteringItemDto("1");
        CarFilteringDto carFilteringDto = new CarFilteringDto(List.of(List.of(carFilteringItemDto)));
        when(carFilteringStrategy.filterCars()).thenReturn(carFilteringDto);
        when(carSimilarityStrategy.findSimilarCars(any(CarSimilarityInputDto.class))).thenReturn(null);

        assertThrows(GenericServiceException.class, () -> carSimilarityServiceImpl.calculateSimilarities());
    }
}