package com.gk.car.similarity.strategies.impl;

import static com.gk.car.similarity.constants.Constants.LABEL_SIMILARITY_STRATEGY;

import com.gk.car.commons.entities.CarFeatureEntity;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.enums.FeatureType;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.commons.repository.CarFeatureRepository;
import com.gk.car.similarity.dto.CarSimilarityInputDto;
import com.gk.car.similarity.dto.CarSimilarityInputItemDto;
import com.gk.car.similarity.dto.CarSimilarityOutputDto;
import com.gk.car.similarity.dto.CarSimilarityOutputItemDto;
import com.gk.car.similarity.dto.LabelBasedCarSimilarityItem;
import com.gk.car.similarity.strategies.CarSimilarityStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component(LABEL_SIMILARITY_STRATEGY)
public class LabelCarSimilarityStrategy implements CarSimilarityStrategy {

  private final CarFeatureRepository carFeatureRepository;

  @Value("${car.similarity.limit:10}")
  private int similarityLimit;

  @Override
  public CarSimilarityOutputDto findSimilarCars(CarSimilarityInputDto carSimilarityInputDto) {
    if(Objects.isNull(carSimilarityInputDto) || Objects.isNull(carSimilarityInputDto.getCars()) || carSimilarityInputDto.getCars().isEmpty()) {
      return CarSimilarityOutputDto.builder().similarityMap(new HashMap<>()).build();
    }
    Map<String, Set<CarFeatureEntity>> featureMap = processVariants(carSimilarityInputDto);
    Map<String, List<CarSimilarityOutputItemDto>> similarCars = new HashMap<>();
    for(String first : featureMap.keySet()) {
      PriorityQueue<LabelBasedCarSimilarityItem> similarityItems = new PriorityQueue<>(Comparator.comparingInt(
          LabelBasedCarSimilarityItem::getScore));
      for(String second: featureMap.keySet()) {
        if(first.equals(second)) continue;
        int score = findScore(featureMap.get(first), featureMap.get(second));
        if(score == 0) continue;
        if(similarityItems.size() < similarityLimit) {
          similarityItems.add(LabelBasedCarSimilarityItem.builder()
                  .carVariantId(second)
                  .score(score)
              .build());
        }else if(similarityItems.peek().getScore() < score) {
          similarityItems.poll();
          similarityItems.add(LabelBasedCarSimilarityItem.builder()
                  .carVariantId(second)
                  .score(score)
              .build());
        }
      }
      List<CarSimilarityOutputItemDto> firstSimilarCars = new ArrayList<>();
      while (!similarityItems.isEmpty()) {
        LabelBasedCarSimilarityItem similarityItem = similarityItems.poll();
        firstSimilarCars.add(0, CarSimilarityOutputItemDto.builder()
                .carVariantId(similarityItem.getCarVariantId())
            .build());
      }
      similarCars.put(first, firstSimilarCars);
    }
    return CarSimilarityOutputDto.builder()
        .similarityMap(similarCars)
        .build();
  }

  private int findScore(Set<CarFeatureEntity> firstFeatures, Set<CarFeatureEntity> secondFeatures) {
    int score = 0;
    Set<String> secondFeatureIds = secondFeatures.stream().map(CarFeatureEntity::getFeatureId).collect(Collectors.toSet());
    for(CarFeatureEntity feature : firstFeatures) {
      if(FeatureType.CLASSIFICATION.equals(feature.getFeatureType()) || FeatureType.BINARY.equals(feature.getFeatureType())) {
        if(secondFeatureIds.contains(feature.getFeatureId())) score++;
      }
    }
    return score;
  }

  private Map<String, Set<CarFeatureEntity>> processVariants(CarSimilarityInputDto carSimilarityInputDto) {
    List<String> carVariants = carSimilarityInputDto.getCars().stream().map(CarSimilarityInputItemDto::getCarVariantId).toList();
    List<CarFeatureEntity> features = carFeatureRepository.findAllByCarVariantIdIn(carVariants);
    Map<String, Set<CarFeatureEntity>> featureMap = new HashMap<>();
    for(CarFeatureEntity carFeature : features) {
      featureMap.computeIfAbsent(carFeature.getCarVariantId(), k -> new HashSet<>()).add(carFeature);
    }
    return featureMap;
  }
}
