package com.token.authenticate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(AppException.class) // 특정 exception을 받아 처리할 수 있다.
    public ResponseEntity<?> appExceptionHandler(AppException e) { // runtimeException에러를 처리하는 handler
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode().name()+" "+e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class) // 특정 exception을 받아 처리할 수 있다.
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) { // runtimeException에러를 처리하는 handler
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
