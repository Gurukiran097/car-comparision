package com.gk.car.similarity.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gk.car.similarity.dto.CarFilteringDto;
import com.gk.car.similarity.dto.CarSimilarityInputDto;
import com.gk.car.similarity.dto.CarSimilarityOutputDto;
import com.gk.car.similarity.services.CarSimilarityService;
import com.gk.car.similarity.strategies.CarFilteringStrategy;
import com.gk.car.similarity.strategies.CarSimilarityStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SimilarityHousekeepingControllerTest {

    private SimilarityHousekeepingController controller;

    @Mock
    private CarFilteringStrategy carFilteringStrategy;

    @Mock
    private CarSimilarityStrategy carSimilarityStrategy;

    @Mock
    private CarSimilarityService carSimilarityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new SimilarityHousekeepingController(carFilteringStrategy, carSimilarityStrategy, carSimilarityService);
    }

    @Test
    void fetchFilteredCars_returnsFilteredCars() {
        CarFilteringDto expectedDto = new CarFilteringDto();
        when(carFilteringStrategy.filterCars()).thenReturn(expectedDto);

        CarFilteringDto result = controller.fetchFilteredCars();

        assertNotNull(result);
        assertEquals(expectedDto, result);
    }

    @Test
    void filterAndFindSimilar_invokesCalculateSimilarities() {
        doNothing().when(carSimilarityService).calculateSimilarities();

        controller.filterAndFindSimilar();

        verify(carSimilarityService, times(1)).calculateSimilarities();
    }
}