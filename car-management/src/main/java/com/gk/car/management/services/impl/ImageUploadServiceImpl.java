package com.gk.car.management.services.impl;

import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.management.services.ImageUploadService;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class ImageUploadServiceImpl implements ImageUploadService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    @Override
    public String uploadImage(MultipartFile file) {
        if (!file.getContentType().startsWith("image/")) {
            throw new GenericServiceException(ErrorCode.INVALID_DATA, "File must be an image");
        }
        String fileName = UUID.randomUUID().toString();

        try {
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName).build();
            storage.create(blobInfo, file.getBytes());
        } catch (IOException e) {
            log.error("Failed to upload image", e);
            throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, "Failed to upload image");
        }
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
}