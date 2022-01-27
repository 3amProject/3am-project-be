package com.tam.threeam.response;


import com.tam.threeam.response.Exception.ApiException;
import com.tam.threeam.response.Exception.InvalidRefreshTokenException;
import com.tam.threeam.response.Exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler(final ApiException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity.builder()
                        .code(e.getError().getCode())
                        .message(e.getError().getMessage())
                        .errorDetail(e.getError().getErrorDetail())
                        .build());
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<Map<String , Object>> handleTokenException(InvalidTokenException e){
        e.printStackTrace();
        Map<String,  Object> data = new HashMap<>();
        data.put("code", 4401);
        data.put("message","invalid token");
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(data);
    }

    @ExceptionHandler({InvalidRefreshTokenException.class})
    public Map<String , Object> handleRefreshTokenException(InvalidRefreshTokenException e){
        e.printStackTrace();
        Map<String,  Object> data = new HashMap<>();
        data.put("code", 4402);
        data.put("message","invalid token");
        return data;
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
