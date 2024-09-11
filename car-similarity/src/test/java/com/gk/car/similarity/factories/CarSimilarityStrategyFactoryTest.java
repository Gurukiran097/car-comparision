package com.gk.car.similarity.factories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.similarity.strategies.CarSimilarityStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

class CarSimilarityStrategyFactoryTest {

    private CarSimilarityStrategyFactory factory;

    @Mock
    private Map<String, CarSimilarityStrategy> strategyMap;

    @Mock
    private CarSimilarityStrategy mockStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        factory = new CarSimilarityStrategyFactory(strategyMap);
    }

    @Test
    void getSimilarityStrategy_withValidStrategy_returnsStrategy() {
        String validStrategy = "validStrategy";
        when(strategyMap.containsKey(validStrategy)).thenReturn(true);
        when(strategyMap.get(validStrategy)).thenReturn(mockStrategy);

        CarSimilarityStrategy result = factory.getSimilarityStrategy(validStrategy);

        assertNotNull(result);
        assertEquals(mockStrategy, result);
    }

    @Test
    void getSimilarityStrategy_withInvalidStrategy_throwsGenericServiceException() {
        String invalidStrategy = "invalidStrategy";
        when(strategyMap.containsKey(invalidStrategy)).thenReturn(false);

        GenericServiceException exception = assertThrows(GenericServiceException.class, () -> {
            factory.getSimilarityStrategy(invalidStrategy);
        });

        assertEquals(ErrorCode.INVALID_SIMILARITY_STRATEGY, exception.getErrorCode());
    }

    @Test
    void getSimilarityStrategy_withNullStrategy_throwsGenericServiceException() {
        String nullStrategy = null;

        GenericServiceException exception = assertThrows(GenericServiceException.class, () -> {
            factory.getSimilarityStrategy(nullStrategy);
        });

        assertEquals(ErrorCode.INVALID_SIMILARITY_STRATEGY, exception.getErrorCode());
    }
}