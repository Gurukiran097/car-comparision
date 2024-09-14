package com.gk.car.management.services;

import com.gk.car.commons.dto.AddCarDto;
import com.gk.car.commons.dto.AddCarFeatureDto;
import com.gk.car.commons.dto.AddCarVariantDto;
import com.gk.car.commons.enums.CarType;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.management.clients.CarDataClient;
import com.gk.car.management.services.impl.CarManagementServiceImpl;
import feign.FeignException;
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

class CarManagementServiceImplTest {

    @Mock
    private CarDataClient carDataClient;

    @InjectMocks
    private CarManagementServiceImpl carManagementServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCar_withValidData_callsCarDataClient() {
        AddCarDto addCarDto = new AddCarDto("CarName", "Manufacturer", CarType.COMPACT, null);

        carManagementServiceImpl.addCar(addCarDto);

        verify(carDataClient).addCar(addCarDto);
    }

    @Test
    void addCar_withNullDto_throwsGenericServiceException() {
        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCar(null));
    }

    @Test
    void addCar_withMissingCarName_throwsGenericServiceException() {
        AddCarDto addCarDto = new AddCarDto(null, "Manufacturer", CarType.HATCHBACK, null);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCar(addCarDto));
    }

    @Test
    void addCar_withMissingCarType_throwsGenericServiceException() {
        AddCarDto addCarDto = new AddCarDto("CarName", null, CarType.COMPACT, null);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCar(addCarDto));
    }

    @Test
    void addCar_withMissingManufacturer_throwsGenericServiceException() {
        AddCarDto addCarDto = new AddCarDto("CarName", "Manufacturer", null, null);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCar(addCarDto));
    }

    @Test
    void addCar_whenFeignExceptionIsThrown_throwsGenericServiceException() {
        AddCarDto addCarDto = new AddCarDto("CarName", "Manufacturer", CarType.COMPACT, null);

        FeignClientException feignClientException = new FeignClientException.BadRequest("Bad Request", Request.create(Request.HttpMethod.GET, "", Map.of(), null, null, null), null, null);
        doThrow(feignClientException).when(carDataClient).addCar(addCarDto);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCar(addCarDto));
    }

    @Test
    void addVariant_withValidData_callsCarDataClient() {
        AddCarVariantDto addCarVariantDto = new AddCarVariantDto("VariantName", "ImageUrl", null);
        String carId = "1";

        carManagementServiceImpl.addVariant(addCarVariantDto, carId);

        verify(carDataClient).addCarVariant(carId, addCarVariantDto);
    }

    @Test
    void addVariant_withNullDto_throwsGenericServiceException() {
        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addVariant(null, "1"));
    }

    @Test
    void addVariant_withMissingVariantName_throwsGenericServiceException() {
        AddCarVariantDto addCarVariantDto = new AddCarVariantDto(null, "ImageUrl", null);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addVariant(addCarVariantDto, "1"));
    }

    @Test
    void addVariant_withMissingImageUrl_throwsGenericServiceException() {
        AddCarVariantDto addCarVariantDto = new AddCarVariantDto("VariantName", null, null);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addVariant(addCarVariantDto, "1"));
    }

    @Test
    void addVariant_withNullCarId_throwsGenericServiceException() {
        AddCarVariantDto addCarVariantDto = new AddCarVariantDto("VariantName", "ImageUrl", null);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addVariant(addCarVariantDto, null));
    }

    @Test
    void addVariant_whenFeignClientExceptionIsThrown_throwsGenericServiceException() {
        AddCarVariantDto addCarVariantDto = new AddCarVariantDto("VariantName", "ImageUrl", null);
        String carId = "1";
        FeignClientException feignClientException = new FeignClientException.BadRequest("Bad Request", Request.create(Request.HttpMethod.GET, "", Map.of(), null, null, null), null, null);
        doThrow(feignClientException).when(carDataClient).addCarVariant(carId, addCarVariantDto);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addVariant(addCarVariantDto, carId));
    }

    @Test
    void addCarFeature_withValidData_callsCarDataClient() {
        AddCarFeatureDto addCarFeatureDto = new AddCarFeatureDto("FeatureId", 1);
        String carVariantId = "1";

        carManagementServiceImpl.addCarFeature(addCarFeatureDto, carVariantId);

        verify(carDataClient).addCarFeature(carVariantId, addCarFeatureDto);
    }

    @Test
    void addCarFeature_withNullDto_throwsGenericServiceException() {
        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCarFeature(null, "1"));
    }

    @Test
    void addCarFeature_withMissingFeatureId_throwsGenericServiceException() {
        AddCarFeatureDto addCarFeatureDto = new AddCarFeatureDto(null, null);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCarFeature(addCarFeatureDto, "1"));
    }

    @Test
    void addCarFeature_withNullCarVariantId_throwsGenericServiceException() {
        AddCarFeatureDto addCarFeatureDto = new AddCarFeatureDto("FeatureId", null);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCarFeature(addCarFeatureDto, null));
    }

    @Test
    void addCarFeature_whenFeignClientExceptionIsThrown_throwsGenericServiceException() {
        AddCarFeatureDto addCarFeatureDto = new AddCarFeatureDto("FeatureId", null);
        String carVariantId = "1";
        FeignClientException feignClientException = new FeignClientException.BadRequest("Bad Request", Request.create(Request.HttpMethod.GET, "", Map.of(), null, null, null), null, null);
        doThrow(feignClientException).when(carDataClient).addCarFeature(carVariantId, addCarFeatureDto);

        assertThrows(GenericServiceException.class, () -> carManagementServiceImpl.addCarFeature(addCarFeatureDto, carVariantId));
    }
}