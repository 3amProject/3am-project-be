package com.tam.threeam.response;


import com.tam.threeam.response.Exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

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

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<BaseResponseDTO> handleTokenException(ExpiredJwtException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED.value())
                .body(BaseResponseDTO.builder()
                        .code("ER010")
                        .messageType("FAIL")
                        .message("AccessToken이 만료되었습니다.")
                        .build());
    }


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiExceptionEntity> exceptionHandler ( final RuntimeException e) {
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
