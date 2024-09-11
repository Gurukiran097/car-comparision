package com.gk.car.data.test.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.car.commons.constants.KafkaConstants;
import com.gk.car.commons.dto.CarSimilarityUpdateDto;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.data.listeners.CarSimilarityUpdateListener;
import com.gk.car.data.services.impl.CarWriteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CarSimilarityUpdateListenerTest {

    @Mock
    private CarWriteServiceImpl carManagementService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Acknowledgment acknowledgment;

    @InjectMocks
    private CarSimilarityUpdateListener carSimilarityUpdateListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consume_withValidMessage_callsUpdateSimilarCars() throws Exception {
        String message = "{\"carId\":\"1\"}";
        CarSimilarityUpdateDto carSimilarityUpdateDto = new CarSimilarityUpdateDto();
        when(objectMapper.readValue(message, CarSimilarityUpdateDto.class)).thenReturn(carSimilarityUpdateDto);

        carSimilarityUpdateListener.consume(message, acknowledgment);

        verify(carManagementService).updateSimilarCars(carSimilarityUpdateDto);
        verify(acknowledgment).acknowledge();
    }

    @Test
    void consume_withInvalidMessage_throwsGenericServiceException() throws Exception {
        String message = "invalid";
        when(objectMapper.readValue(message, CarSimilarityUpdateDto.class)).thenThrow(JsonProcessingException.class);

        assertThrows(GenericServiceException.class, () -> carSimilarityUpdateListener.consume(message, acknowledgment));
    }
}