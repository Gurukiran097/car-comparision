package com.gk.car.management.services;

import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.management.services.ImageUploadService;
import com.gk.car.management.services.impl.ImageUploadServiceImpl;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImageUploadServiceImplTest {

    @Mock
    private Storage storage;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private ImageUploadServiceImpl imageUploadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(imageUploadService, "bucketName", "recon_test");
    }

    @Test
    void uploadImage_success() throws IOException {
        when(file.getContentType()).thenReturn("image/png");
        when(file.getBytes()).thenReturn(new byte[0]);
        when(storage.create(any(BlobInfo.class), any(byte[].class))).thenReturn(null);

        String imageUrl = imageUploadService.uploadImage(file);

        assertNotNull(imageUrl);
        assertTrue(imageUrl.contains("https://storage.googleapis.com/"));
    }

    @Test
    void uploadImage_invalidFileType_throwsException() {
        when(file.getContentType()).thenReturn("text/plain");

        GenericServiceException exception = assertThrows(GenericServiceException.class, () -> {
            imageUploadService.uploadImage(file);
        });

        assertEquals(ErrorCode.INVALID_DATA, exception.getErrorCode());
    }

    @Test
    void uploadImage_storageException_throwsException() throws IOException {
        when(file.getContentType()).thenReturn("image/png");
        when(file.getBytes()).thenThrow(new IOException("Storage error"));

        GenericServiceException exception = assertThrows(GenericServiceException.class, () -> {
            imageUploadService.uploadImage(file);
        });

        assertEquals(ErrorCode.UNKNOWN_ERROR, exception.getErrorCode());
    }
}