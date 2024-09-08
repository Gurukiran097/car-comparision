package com.gk.car.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

  INVALID_FEATURE("INVALID_FEATURE", "Feature Id is invalid", HttpStatus.BAD_REQUEST),
  INVALID_FEATURE_CONFIGURATION("INVALID_FEATURE_CONFIGURATION", "Feature data is invalid", HttpStatus.BAD_REQUEST)
  ;

  private final String errorCode;
  private final String message;
  private final HttpStatus httpStatus;
}
