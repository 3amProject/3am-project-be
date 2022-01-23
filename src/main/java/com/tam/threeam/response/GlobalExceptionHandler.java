package com.tam.threeam.response;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(final ApiException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity.builder()
                        .status(e.getError().getStatus())
                        .code(e.getError().getCode())
                        .message(e.getError().getMessage())
                        .errorDetail(e.getError().getErrorDetail())
                        .build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(final RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity.builder()
                        .code(ExceptionEnum.RUNTIME_EXCEPTION.getCode())
                        .message(e.getMessage())
                        .errorDetail(ExceptionEnum.RUNTIME_EXCEPTION.getErrorDetail())
                        .build());
    }






}
