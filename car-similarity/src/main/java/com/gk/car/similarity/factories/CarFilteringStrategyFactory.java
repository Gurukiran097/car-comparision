package com.gk.car.similarity.factories;

import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.similarity.strategies.CarFilteringStrategy;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarFilteringStrategyFactory {
  private final Map<String, CarFilteringStrategy> filterMap;

  public CarFilteringStrategy getFilteringStrategy(String filter) {
    if(filterMap.containsKey(filter)) {
      return filterMap.get(filter);
    }
    throw new GenericServiceException(ErrorCode.INVALID_FILTER);
  }
}
