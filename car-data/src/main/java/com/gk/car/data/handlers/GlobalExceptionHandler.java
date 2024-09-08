package com.gk.car.data.handlers;

import com.gk.car.data.exceptions.GenericServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(GenericServiceException.class)
  public ResponseEntity<String> handleGenericServiceException(GenericServiceException genericServiceException) {
    return ResponseEntity.status(genericServiceException.getErrorCode().getHttpStatus()).body(genericServiceException.getMessage());
  }
}
