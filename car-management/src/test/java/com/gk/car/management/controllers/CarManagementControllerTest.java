package com.gk.car.management.controllers;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.management.services.CarManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class CarManagementControllerTest {

    @Mock
    private CarManagementService carManagementService;

    private MockMvc mockMvc;

    @InjectMocks
    private CarManagementController carManagementController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carManagementController).build();
    }

    @Test
    void addCar_returnsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/management/car/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"carName\":\"Test Car\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(carManagementService).addCar(any(AddCarDto.class));
    }

    @Test
    void addCarVariant_returnsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/management/car/variant/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"variantName\":\"Test Variant\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(carManagementService).addVariant(any(AddCarVariantDto.class), any(String.class));
    }


    @Test
    void addCarFeature_returnsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/management/car/feature/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"featureId\":\"123\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(carManagementService).addCarFeature(any(AddCarFeatureDto.class), any(String.class));
    }
}