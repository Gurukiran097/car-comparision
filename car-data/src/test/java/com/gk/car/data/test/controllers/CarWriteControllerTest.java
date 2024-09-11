package com.gk.car.data.test.controllers;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.data.controllers.CarWriteController;
import com.gk.car.data.services.CarWriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CarWriteControllerTest {

  @Mock
  private CarWriteService carWriteService;

  @InjectMocks
  private CarWriteController carWriteController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(carWriteController).build();
  }

  @Test
  void addCar_returnsSuccess() throws Exception {
    when(carWriteService.addCar(any(AddCarDto.class))).thenReturn("id");

    mockMvc.perform(post("/v1/api/write/car/")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"Test Car\"}"))
        .andExpect(status().isOk());

    Mockito.verify(carWriteService,Mockito.times(1)).addCar(any(AddCarDto.class));
  }

  @Test
  void addCarVariant_returnsSuccess() throws Exception {
    when(carWriteService.addVariant(any(AddCarVariantDto.class), anyString())).thenReturn("id");

    mockMvc.perform(post("/v1/api/write/car/variant/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"variantName\":\"Test Variant\"}"))
        .andExpect(status().isOk());

    Mockito.verify(carWriteService,Mockito.times(1)).addVariant(any(AddCarVariantDto.class), anyString());
  }

  @Test
  void addCarFeature_returnsSuccess() throws Exception {

    mockMvc.perform(post("/v1/api/write/car/feature/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"featureName\":\"Test Feature\"}"))
        .andExpect(status().isOk());

    Mockito.verify(carWriteService,Mockito.times(1)).addCarFeature(any(AddCarFeatureDto.class), anyString());
  }
}
