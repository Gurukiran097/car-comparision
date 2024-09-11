package com.gk.car.data.test.controllers;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.data.controllers.CarReadController;
import com.gk.car.data.dto.CarSimilarityDto;
import com.gk.car.data.dto.CarVariantDto;
import com.gk.car.data.dto.CarVariantListDto;
import com.gk.car.data.services.CarReadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class CarReadControllerTest {

  @Mock
  private CarReadService carReadService;

  @InjectMocks
  private CarReadController carReadController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(carReadController).build();
  }

  @Test
  void getCar_returnsCarVariantDto() throws Exception {
    CarVariantDto carVariantDto = new CarVariantDto();
    when(carReadService.getCar(anyString())).thenReturn(carVariantDto);

    mockMvc.perform(get("/v1/api/car/{carVariantId}", "1"))
        .andExpect(status().isOk());

    Mockito.verify(carReadService,Mockito.times(1)).getCar(anyString());

  }

  @Test
  void getCars_returnsCarVariantListDto() throws Exception {
    CarVariantListDto carVariantListDto = new CarVariantListDto();
    when(carReadService.getCars(anyList())).thenReturn(carVariantListDto);

    mockMvc.perform(post("/v1/api/car/compare")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[\"1\", \"2\"]"))
        .andExpect(status().isOk());

    Mockito.verify(carReadService,Mockito.times(1)).getCars(anyList());
  }

  @Test
  void getCarDifferences_returnsCarVariantListDto() throws Exception {
    CarVariantListDto carVariantListDto = new CarVariantListDto();
    when(carReadService.getCarDifferences(anyList())).thenReturn(carVariantListDto);

    mockMvc.perform(post("/v1/api/car/compare/differences")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[\"1\", \"2\"]"))
        .andExpect(status().isOk());

    Mockito.verify(carReadService,Mockito.times(1)).getCarDifferences(anyList());

  }

  @Test
  void getSimilarCars_returnsCarSimilarityDto() throws Exception {
    CarSimilarityDto carSimilarityDto = new CarSimilarityDto();
    when(carReadService.getSimilarCars(anyString())).thenReturn(carSimilarityDto);

    mockMvc.perform(get("/v1/api/car/similar/{carVariantId}", "1"))
        .andExpect(status().isOk());

    Mockito.verify(carReadService,Mockito.times(1)).getSimilarCars(anyString());
  }

}