package com.gk.car.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

  INVALID_FEATURE("INVALID_FEATURE", "Feature Id is invalid", HttpStatus.BAD_REQUEST),
  INVALID_FEATURE_CONFIGURATION("INVALID_FEATURE_CONFIGURATION", "Feature data is invalid", HttpStatus.BAD_REQUEST),
  INVALID_FILTER("INVALID_FILTER", "Filter Id is invalid", HttpStatus.BAD_REQUEST),
  INVALID_SIMILARITY_STRATEGY("INVALID_SIMILARITY_STRATEGY", "Similarity strategy not present", HttpStatus.BAD_REQUEST),
  INVALID_CAR_VARIANT_ID("INVALID_CAR_VARIANT_ID", "Invalid car variant Id", HttpStatus.BAD_REQUEST),
  INVALID_CAR_VARIANT_SIZE_MIN("INVALID_CAR_VARIANT_SIZE_MIN", "Car variants should have atleast 2 cars", HttpStatus.BAD_REQUEST),
  INVALID_CAR_VARIANT_SIZE_MAX("INVALID_CAR_VARIANT_SIZE_MIN", "Car variants should have at max 3 cars", HttpStatus.BAD_REQUEST),
  UNKNOWN_ERROR("UNKNOWN_ERROR", "Error not known", HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_CAR_ID("INVALID_CAR_ID", "Invalid car Id", HttpStatus.BAD_REQUEST),
  INVALID_DATA("INVALID_DATA", "Invalid data", HttpStatus.BAD_REQUEST),
  ;

  private final String errorCode;
  private final String message;
  private final HttpStatus httpStatus;
}
