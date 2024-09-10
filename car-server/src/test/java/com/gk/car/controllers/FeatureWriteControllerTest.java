package com.gk.car.controllers;

import static org.mockito.ArgumentMatchers.any;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.data.controllers.FeatureWriteController;
import com.gk.car.data.services.FeatureWriteService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class FeatureWriteControllerTest {

  @Mock
  private FeatureWriteService featureWriteService;

  private MockMvc mockMvc;

  @InjectMocks
  private FeatureWriteController featureWriteController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(featureWriteController).build();
  }

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @SneakyThrows
  void addFeature_returnsSuccess() {
    mockMvc.perform(post("/v1/api/write/feature/")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content("{\"featureName\":\"Test Feature\"}"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(this.featureWriteService,Mockito.times(1)).addFeature(any(AddFeatureDto.class));
  }
}
