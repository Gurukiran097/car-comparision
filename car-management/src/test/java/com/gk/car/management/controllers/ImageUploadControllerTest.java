package com.gk.car.management.controllers;

import com.gk.car.management.services.ImageUploadService;
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
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ImageUploadControllerTest {

    @Mock
    private ImageUploadService imageUploadService;

    private MockMvc mockMvc;

    @InjectMocks
    private ImageUploadController imageUploadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(imageUploadController).build();
    }

    @Test
    void uploadImage_returnsImageUrl() throws Exception {
        when(imageUploadService.uploadImage(any(MultipartFile.class))).thenReturn("https://storage.googleapis.com/bucket/image.png");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/v1/api/management/image/upload")
                .file("file", "image content".getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("https://storage.googleapis.com/bucket/image.png"));
    }

}