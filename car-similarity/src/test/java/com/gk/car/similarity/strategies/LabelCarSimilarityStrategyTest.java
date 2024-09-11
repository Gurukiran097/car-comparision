package com.gk.car.similarity.strategies;

import com.gk.car.commons.entities.CarFeatureEntity;
import com.gk.car.commons.enums.FeatureType;
import com.gk.car.commons.repository.CarFeatureRepository;
import com.gk.car.similarity.dto.CarSimilarityInputDto;
import com.gk.car.similarity.dto.CarSimilarityInputItemDto;
import com.gk.car.similarity.dto.CarSimilarityOutputDto;
import com.gk.car.similarity.dto.CarSimilarityOutputItemDto;
import com.gk.car.similarity.strategies.impl.LabelCarSimilarityStrategy;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class LabelCarSimilarityStrategyTest {

    @Mock
    private CarFeatureRepository carFeatureRepository;

    @InjectMocks
    private LabelCarSimilarityStrategy labelCarSimilarityStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(labelCarSimilarityStrategy, "similarityLimit", 10);
    }

    @Test
    void findSimilarCars_withEmptyInput_returnsEmptySimilarityMap() {
        CarSimilarityInputDto inputDto = new CarSimilarityInputDto(List.of());

        CarSimilarityOutputDto result = labelCarSimilarityStrategy.findSimilarCars(inputDto);

        assertEquals(0, result.getSimilarityMap().size());
    }

    @Test
    void findSimilarCars_withNullInput_returnsEmptySimilarityMap() {
        CarSimilarityOutputDto result = labelCarSimilarityStrategy.findSimilarCars(null);

        assertEquals(0, result.getSimilarityMap().size());
    }

    @Test
    void findSimilarCars_withNullCarList_returnsEmptySimilarityMap() {
        CarSimilarityInputDto inputDto = new CarSimilarityInputDto(null);
        CarSimilarityOutputDto result = labelCarSimilarityStrategy.findSimilarCars(inputDto);

        assertEquals(0, result.getSimilarityMap().size());
    }

    @Test
    void findSimilarCars_withNoMatchingFeatures_returnsEmptySimilarityMap() {
        CarSimilarityInputDto inputDto = new CarSimilarityInputDto(List.of(
                new CarSimilarityInputItemDto("1"),
                new CarSimilarityInputItemDto("2")
        ));

        when(carFeatureRepository.findAllByCarVariantIdIn(List.of("1", "2"))).thenReturn(List.of());

        CarSimilarityOutputDto result = labelCarSimilarityStrategy.findSimilarCars(inputDto);

        assertEquals(0, result.getSimilarityMap().size());
    }

    @Test
    void findSimilarCars_withMoreThanTenCars_returnsAtMostTenSimilarCars() {
        List<CarSimilarityInputItemDto> inputItems = List.of(
                new CarSimilarityInputItemDto("1"), new CarSimilarityInputItemDto("2"),
                new CarSimilarityInputItemDto("3"), new CarSimilarityInputItemDto("4"),
                new CarSimilarityInputItemDto("5"), new CarSimilarityInputItemDto("6"),
                new CarSimilarityInputItemDto("7"), new CarSimilarityInputItemDto("8"),
                new CarSimilarityInputItemDto("9"), new CarSimilarityInputItemDto("10"),
                new CarSimilarityInputItemDto("11"), new CarSimilarityInputItemDto("12"),
                new CarSimilarityInputItemDto("13"), new CarSimilarityInputItemDto("14"),
                new CarSimilarityInputItemDto("15")
        );
        CarSimilarityInputDto inputDto = new CarSimilarityInputDto(inputItems);

        List<CarFeatureEntity> features = List.of(
                new CarFeatureEntity("1", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("2", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("3", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("4", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("5", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("6", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("7", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("8", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("9", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("10", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("11", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("12", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("13", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("14", "feature1", FeatureType.CLASSIFICATION, null),
                new CarFeatureEntity("15", "feature1", FeatureType.CLASSIFICATION, null)
        );

        when(carFeatureRepository.findAllByCarVariantIdIn(anyList())).thenReturn(features);

        CarSimilarityOutputDto result = labelCarSimilarityStrategy.findSimilarCars(inputDto);

        for (String carVariantId : result.getSimilarityMap().keySet()) {
            assertTrue(result.getSimilarityMap().get(carVariantId).size() == 10);
        }
    }

    @Test
    void findSimilarCars_withMoreThanTenCars_returnsTopTenHighestScoreCarsInOrder() {
        List<CarSimilarityInputItemDto> inputItems = List.of(
            new CarSimilarityInputItemDto("1"), new CarSimilarityInputItemDto("2"),
            new CarSimilarityInputItemDto("3"), new CarSimilarityInputItemDto("4"),
            new CarSimilarityInputItemDto("5"), new CarSimilarityInputItemDto("6"),
            new CarSimilarityInputItemDto("7"), new CarSimilarityInputItemDto("8"),
            new CarSimilarityInputItemDto("9"), new CarSimilarityInputItemDto("10"),
            new CarSimilarityInputItemDto("11"), new CarSimilarityInputItemDto("12"),
            new CarSimilarityInputItemDto("13"), new CarSimilarityInputItemDto("14"),
            new CarSimilarityInputItemDto("15")
        );
        CarSimilarityInputDto inputDto = new CarSimilarityInputDto(inputItems);

        List<CarFeatureEntity> features = List.of(
            new CarFeatureEntity("1", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("2", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("3", "feature2", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("4", "feature2", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("5", "feature3", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("6", "feature3", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("7", "feature4", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("8", "feature4", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("9", "feature5", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("10", "feature5", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("11", "feature6", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("12", "feature6", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("13", "feature7", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("14", "feature7", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("15", "feature8", FeatureType.CLASSIFICATION, null)
        );

        when(carFeatureRepository.findAllByCarVariantIdIn(anyList())).thenReturn(features);

        CarSimilarityOutputDto result = labelCarSimilarityStrategy.findSimilarCars(inputDto);

        for (String carVariantId : result.getSimilarityMap().keySet()) {
            List<CarSimilarityOutputItemDto> similarCars = result.getSimilarityMap().get(carVariantId);
            assertTrue(similarCars.size() <= 10);
            Set<String> carFeatureIds = features.stream()
                .filter(f -> f.getCarVariantId().equals(carVariantId))
                .map(CarFeatureEntity::getFeatureId)
                .collect(Collectors.toSet());
            for (CarSimilarityOutputItemDto similarCar : similarCars) {
                Set<String> similarCarFeatureIds = features.stream()
                    .filter(f -> f.getCarVariantId().equals(similarCar.getCarVariantId()))
                    .map(CarFeatureEntity::getFeatureId)
                    .collect(Collectors.toSet());
                assertTrue(carFeatureIds.stream().anyMatch(similarCarFeatureIds::contains));
            }
        }
    }

    @Test
    void findSimilarCars_withMoreThanTenSimilarCars_replacesLowestScoreWhenHigherScoreFound() {
        List<CarSimilarityInputItemDto> inputItems = List.of(
            new CarSimilarityInputItemDto("1"), new CarSimilarityInputItemDto("2"),
            new CarSimilarityInputItemDto("3"), new CarSimilarityInputItemDto("4"),
            new CarSimilarityInputItemDto("5"), new CarSimilarityInputItemDto("6"),
            new CarSimilarityInputItemDto("7"), new CarSimilarityInputItemDto("8"),
            new CarSimilarityInputItemDto("9"), new CarSimilarityInputItemDto("10"),
            new CarSimilarityInputItemDto("11"), new CarSimilarityInputItemDto("12"),
            new CarSimilarityInputItemDto("13"), new CarSimilarityInputItemDto("14"),
            new CarSimilarityInputItemDto("15"), new CarSimilarityInputItemDto("16"),
            new CarSimilarityInputItemDto("17"), new CarSimilarityInputItemDto("18"),
            new CarSimilarityInputItemDto("19"), new CarSimilarityInputItemDto("20")
        );
        CarSimilarityInputDto inputDto = new CarSimilarityInputDto(inputItems);

        List<CarFeatureEntity> features = List.of(
            new CarFeatureEntity("1", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("1", "feature2", FeatureType.BINARY, null),
            new CarFeatureEntity("2", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("3", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("4", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("5", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("6", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("7", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("8", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("9", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("10", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("11", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("12", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("13", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("14", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("15", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("16", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("17", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("18", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("19", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("20", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("20", "feature2", FeatureType.BINARY, null)
        );

        when(carFeatureRepository.findAllByCarVariantIdIn(anyList())).thenReturn(features);

        CarSimilarityOutputDto result = labelCarSimilarityStrategy.findSimilarCars(inputDto);

        List<CarSimilarityOutputItemDto> similarCarsForCar1 = result.getSimilarityMap().get("1");
        assertNotNull(similarCarsForCar1);
        assertEquals(10, similarCarsForCar1.size());
        assertTrue(similarCarsForCar1.stream().anyMatch(car -> car.getCarVariantId().equals("20")));
    }

    @Test
    void findSimilarCars_withNumericalFeatures_doesNotAffectResults() {
        List<CarSimilarityInputItemDto> inputItems = List.of(
            new CarSimilarityInputItemDto("1"), new CarSimilarityInputItemDto("2"),
            new CarSimilarityInputItemDto("3"), new CarSimilarityInputItemDto("4"),
            new CarSimilarityInputItemDto("5"), new CarSimilarityInputItemDto("6"),
            new CarSimilarityInputItemDto("7"), new CarSimilarityInputItemDto("8"),
            new CarSimilarityInputItemDto("9"), new CarSimilarityInputItemDto("10"),
            new CarSimilarityInputItemDto("11"), new CarSimilarityInputItemDto("12"),
            new CarSimilarityInputItemDto("13"), new CarSimilarityInputItemDto("14"),
            new CarSimilarityInputItemDto("15"), new CarSimilarityInputItemDto("16"),
            new CarSimilarityInputItemDto("17"), new CarSimilarityInputItemDto("18"),
            new CarSimilarityInputItemDto("19"), new CarSimilarityInputItemDto("20")
        );
        CarSimilarityInputDto inputDto = new CarSimilarityInputDto(inputItems);

        List<CarFeatureEntity> features = List.of(
            new CarFeatureEntity("1", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("1", "feature2", FeatureType.BINARY, null),
            new CarFeatureEntity("1", "feature3", FeatureType.NUMERICAL, 100),
            new CarFeatureEntity("2", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("3", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("4", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("5", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("6", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("7", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("8", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("9", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("10", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("11", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("12", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("13", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("14", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("15", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("16", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("17", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("18", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("19", "feature1", FeatureType.CLASSIFICATION, null),
            new CarFeatureEntity("20", "feature3", FeatureType.NUMERICAL, 200)
        );

        when(carFeatureRepository.findAllByCarVariantIdIn(anyList())).thenReturn(features);

        CarSimilarityOutputDto result = labelCarSimilarityStrategy.findSimilarCars(inputDto);

        List<CarSimilarityOutputItemDto> similarCarsForCar1 = result.getSimilarityMap().get("1");
        assertNotNull(similarCarsForCar1);
        assertEquals(10, similarCarsForCar1.size());
        assertTrue(similarCarsForCar1.stream().noneMatch(car -> car.getCarVariantId().equals("20")));
    }
}