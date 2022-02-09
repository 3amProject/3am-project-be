package com.tam.threeam.response.Exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author 이동은
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/01/12
 * @
 * @ 수정일         수정자                수정내용
 * @ ———          ————             —————————————
 * @ 2022/01/12   최초 작성            ExceptionEntity sample -> BaseResponseDto로 리턴타입 대체
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiExceptionEntity {

    private int status;
    private String code;
    private String message;
    private String errorDetail;


}
