package com.gk.car.similarity.strategies.impl;

import com.gk.car.commons.entities.CarFeatureEntity;
import com.gk.car.commons.entities.CarVariantEntity;
import com.gk.car.commons.enums.FeatureType;
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
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LabelBasedCarSimilarityStrategy implements CarSimilarityStrategy {

  private final CarFeatureRepository carFeatureRepository;

  private final int similarityLimit = 10; // PARAMETERIZE IT

  @Override
  public CarSimilarityOutputDto findSimilarCars(CarSimilarityInputDto carSimilarityInputDto) {
    Map<String, Set<CarFeatureEntity>> featureMap = processVariants(carSimilarityInputDto);
    Map<String, List<CarSimilarityOutputItemDto>> similarCars = new HashMap<>();
    for(String first : featureMap.keySet()) {
      PriorityQueue<LabelBasedCarSimilarityItem> similarityItems = new PriorityQueue<>(Comparator.comparingInt(
          LabelBasedCarSimilarityItem::getScore));
      for(String second: featureMap.keySet()) {
        if(first.equals(second)) continue;
        int score = findScore(featureMap.get(first), featureMap.get(second));
        if(similarityItems.size() < similarityLimit || similarityItems.peek().getScore() < score) {
          similarityItems.add(LabelBasedCarSimilarityItem.builder()
                  .carVariantId(second)
                  .score(score)
              .build());
        }
      }
      List<CarSimilarityOutputItemDto> firstSimilarCars = new ArrayList<>();
      while (!similarityItems.isEmpty()) {
        LabelBasedCarSimilarityItem similarityItem = similarityItems.poll();
        firstSimilarCars.add(CarSimilarityOutputItemDto.builder()
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
