package com.gk.car.similarity.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class KafkaPublisherTest {

    private KafkaPublisher kafkaPublisher;
    private ObjectMapper objectMapper;
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    void setUp() {
        objectMapper = mock(ObjectMapper.class);
        kafkaTemplate = mock(KafkaTemplate.class);
        kafkaPublisher = new KafkaPublisher(objectMapper, kafkaTemplate);
    }

    @Test
    void send_withValidMessage_sendsMessageSuccessfully() throws JsonProcessingException {
        String topic = "test-topic";
        String key = "test-key";
        Object message = new Object();

        when(objectMapper.writeValueAsString(message)).thenReturn("message");

        kafkaPublisher.send(topic, key, message);

        verify(kafkaTemplate, times(1)).send(topic, key, "message");
    }

    @Test
    void send_withValidMessageWithoutKey_sendsMessageSuccessfully() throws JsonProcessingException {
        String topic = "test-topic";
        Object message = new Object();

        when(objectMapper.writeValueAsString(message)).thenReturn("message");

        kafkaPublisher.send(topic, message);

        verify(kafkaTemplate, times(1)).send(topic, "message");
    }

    @Test
    void send_withJsonProcessingException_throwsGenericServiceException() throws JsonProcessingException {
        String topic = "test-topic";
        String key = "test-key";
        Object message = new Object();

        when(objectMapper.writeValueAsString(message)).thenThrow(JsonProcessingException.class);

        assertThrows(GenericServiceException.class, () -> kafkaPublisher.send(topic, key, message));
    }

    @Test
    void send_withJsonProcessingExceptionWithoutKey_throwsGenericServiceException() throws JsonProcessingException {
        String topic = "test-topic";
        Object message = new Object();

        when(objectMapper.writeValueAsString(message)).thenThrow(JsonProcessingException.class);

        assertThrows(GenericServiceException.class, () -> kafkaPublisher.send(topic, message));
    }
}