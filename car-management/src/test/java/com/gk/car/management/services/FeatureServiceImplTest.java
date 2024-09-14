package com.gk.car.management.services;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.enums.FeatureType;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.management.clients.CarDataClient;
import com.gk.car.management.services.impl.FeatureServiceImpl;
import feign.FeignException.FeignClientException;
import feign.Request;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FeatureServiceImplTest {

    @Mock
    private CarDataClient carDataClient;

    @InjectMocks
    private FeatureServiceImpl featureServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addFeature_withValidData_callsCarDataClient() {
        AddFeatureDto addFeatureDto = new AddFeatureDto("FeatureName", FeatureType.NUMERICAL, "FeatureKey", "FeatureCategory");

        featureServiceImpl.addFeature(addFeatureDto);

        verify(carDataClient).addFeature(addFeatureDto);
    }

    @Test
    void addFeature_withNullDto_throwsGenericServiceException() {
        assertThrows(GenericServiceException.class, () -> featureServiceImpl.addFeature(null));
    }

    @Test
    void addFeature_withMissingFeatureName_throwsGenericServiceException() {
        AddFeatureDto addFeatureDto = new AddFeatureDto(null, FeatureType.NUMERICAL, "FeatureKey", "FeatureCategory");

        assertThrows(GenericServiceException.class, () -> featureServiceImpl.addFeature(addFeatureDto));
    }

    @Test
    void addFeature_withMissingFeatureType_throwsGenericServiceException() {
        AddFeatureDto addFeatureDto = new AddFeatureDto("FeatureName", null, "FeatureKey", "FeatureCategory");

        assertThrows(GenericServiceException.class, () -> featureServiceImpl.addFeature(addFeatureDto));
    }

    @Test
    void addFeature_withMissingFeatureKey_throwsGenericServiceException() {
        AddFeatureDto addFeatureDto = new AddFeatureDto("FeatureName", FeatureType.CLASSIFICATION, null, "FeatureCategory");

        assertThrows(GenericServiceException.class, () -> featureServiceImpl.addFeature(addFeatureDto));
    }

    @Test
    void addFeature_withMissingFeatureCategory_throwsGenericServiceException() {
        AddFeatureDto addFeatureDto = new AddFeatureDto("FeatureName", FeatureType.NUMERICAL, "FeatureKey", null);

        assertThrows(GenericServiceException.class, () -> featureServiceImpl.addFeature(addFeatureDto));
    }

    @Test
    void addFeature_whenFeignClientExceptionIsThrown_throwsGenericServiceException() {
        AddFeatureDto addFeatureDto = new AddFeatureDto("FeatureName", FeatureType.NUMERICAL, "FeatureKey", "FeatureCategory");
        FeignClientException feignClientException = new FeignClientException.BadRequest("Bad Request", Request.create(Request.HttpMethod.GET, "", Map.of(), null, null, null), null, null);
        doThrow(feignClientException).when(carDataClient).addFeature(addFeatureDto);

        assertThrows(GenericServiceException.class, () -> featureServiceImpl.addFeature(addFeatureDto));
    }
}