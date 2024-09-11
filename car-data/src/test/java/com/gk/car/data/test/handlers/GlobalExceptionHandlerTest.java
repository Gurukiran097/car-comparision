package com.gk.car.data.test.handlers;

import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.data.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.gk.car.commons.enums.ErrorCode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleGenericServiceException_returnsCorrectResponse() {
        GenericServiceException exception = new GenericServiceException(ErrorCode.INVALID_CAR_ID, "Error message");
        ResponseEntity<String> response = globalExceptionHandler.handleGenericServiceException(exception);

        assertEquals(exception.getErrorCode().getHttpStatus(), response.getStatusCode());
        assertEquals("Error message", response.getBody());
    }
}