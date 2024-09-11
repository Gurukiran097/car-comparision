package com.gk.car.management.controllers;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.management.services.FeatureService;
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

class FeatureControllerTest {

    @Mock
    private FeatureService featureService;

    private MockMvc mockMvc;

    @InjectMocks
    private FeatureController featureController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(featureController).build();
    }

    @Test
    void addFeature_returnsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/management/feature/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"featureName\":\"Test Feature\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(featureService).addFeature(any(AddFeatureDto.class));
    }
}