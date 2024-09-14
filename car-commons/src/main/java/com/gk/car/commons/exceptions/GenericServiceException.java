package com.gk.car.commons.exceptions;

import com.gk.car.commons.enums.ErrorCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class GenericServiceException extends RuntimeException {

  private final ErrorCode errorCode;

  private final HttpStatus httpStatus;

  public GenericServiceException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
    this.httpStatus = errorCode.getHttpStatus();
    log.error("ErrorCode : {}, Message : {}", errorCode.getErrorCode(), errorCode.getMessage());
  }

  public GenericServiceException(ErrorCode errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.httpStatus = errorCode.getHttpStatus();
    log.error("ErrorCode : {}, Message : {}", errorCode.getErrorCode(), errorMessage);
  }

  public GenericServiceException(ErrorCode errorCode, String errorMessage, HttpStatus httpStatus) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.httpStatus = httpStatus;
    log.error("ErrorCode : {}, Message : {}", errorCode.getErrorCode(), errorMessage);
  }
}
