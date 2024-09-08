package com.gk.car.similarity.factories;

import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.similarity.strategies.CarSimilarityStrategy;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarSimilarityStrategyFactory {
  private final Map<String, CarSimilarityStrategy> filterMap;

  public CarSimilarityStrategy getSimilarityStrategy(String filter) {
    if(filterMap.containsKey(filter)) {
      return filterMap.get(filter);
    }
    throw new GenericServiceException(ErrorCode.INVALID_SIMILARITY_STRATEGY);
  }
}
