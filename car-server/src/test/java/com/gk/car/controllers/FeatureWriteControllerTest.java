package com.gk.car.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.data.controllers.FeatureWriteController;
import com.gk.car.data.services.FeatureWriteService;
import com.gk.car.server.CarComparisionServerApplication;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CarComparisionServerApplication.class)
@AutoConfigureMockMvc
public class FeatureWriteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FeatureWriteService featureWriteService;

  @Autowired
  private ObjectMapper objectMapper;

  private final FeatureWriteController featureWriteController = new FeatureWriteController(featureWriteService);

  @Test
  @SneakyThrows
  void testAddFeature() {
    Mockito.doNothing().when(featureWriteService).addFeature(Mockito.any(AddFeatureDto.class));
    mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/write/feature/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new AddFeatureDto())))
        .andExpect(MockMvcResultMatchers.status().isOk());
    Mockito.verify(this.featureWriteService,Mockito.times(1)).addFeature(Mockito.any(AddFeatureDto.class));
  }


}
