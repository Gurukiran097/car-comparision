package com.gk.car.data.exceptions;

import com.gk.car.commons.enums.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class GenericServiceException extends RuntimeException {

  private final ErrorCode errorCode;

  public GenericServiceException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    log.error("ErrorCode : {}, Message : {}", errorCode.getErrorCode(), errorCode.getMessage());
  }
}
