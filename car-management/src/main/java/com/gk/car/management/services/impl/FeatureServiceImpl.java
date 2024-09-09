package com.gk.car.management.services.impl;

import com.gk.car.commons.dto.AddFeatureDto;
import com.gk.car.commons.enums.ErrorCode;
import com.gk.car.commons.exceptions.GenericServiceException;
import com.gk.car.management.clients.CarDataClient;
import com.gk.car.management.services.FeatureService;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeatureServiceImpl implements FeatureService {

  private final CarDataClient carDataClient;

  @Override
  public void addFeature(AddFeatureDto addFeatureDto) {
    try{
      carDataClient.addFeature(addFeatureDto);
    }catch (FeignClientException e) {
      log.error("Exception while calling car data client", e);
      log.error("Exception inside ", e.getCause());
      throw new GenericServiceException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
    }
  }
}
