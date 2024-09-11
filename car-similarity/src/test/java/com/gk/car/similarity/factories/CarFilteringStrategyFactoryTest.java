package com.gk.car.similarity.factories;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.similarity.strategies.CarFilteringStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

class CarFilteringStrategyFactoryTest {

    private CarFilteringStrategyFactory factory;

    @Mock
    private Map<String, CarFilteringStrategy> filterMap;

    @Mock
    private CarFilteringStrategy mockStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        factory = new CarFilteringStrategyFactory(filterMap);
    }

    @Test
    void getFilteringStrategy_withValidFilter_returnsStrategy() {
        String validFilter = "validFilter";
        when(filterMap.containsKey(validFilter)).thenReturn(true);
        when(filterMap.get(validFilter)).thenReturn(mockStrategy);

        CarFilteringStrategy result = factory.getFilteringStrategy(validFilter);

        assertNotNull(result);
        assertEquals(mockStrategy, result);
    }

    @Test
    void getFilteringStrategy_withInvalidFilter_throwsGenericServiceException() {
        String invalidFilter = "invalidFilter";
        when(filterMap.containsKey(invalidFilter)).thenReturn(false);

        GenericServiceException exception = assertThrows(GenericServiceException.class, () -> {
            factory.getFilteringStrategy(invalidFilter);
        });

        assertEquals(ErrorCode.INVALID_FILTER, exception.getErrorCode());
    }

    @Test
    void getFilteringStrategy_withNullFilter_throwsGenericServiceException() {
        String nullFilter = null;

        GenericServiceException exception = assertThrows(GenericServiceException.class, () -> {
            factory.getFilteringStrategy(nullFilter);
        });

        assertEquals(ErrorCode.INVALID_FILTER, exception.getErrorCode());
    }
}