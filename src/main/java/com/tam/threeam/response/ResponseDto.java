package com.tam.threeam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;


/**
 * @author 이동은
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일         수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2021/12/30     최초 작성
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseDto {

    private int code;
    private Object data;
    private String messageType = "success";
    private String message;

    public static <T>ResponseDto sendSuccess(T data) {
        ResponseDto result = new ResponseDto();
        result.setCode(HttpStatus.OK.value());
        result.setData(data);

        return result;
    }


    //TODO APIExceptionEntity 로 쓰기. error-code 는 code 필드에 담고, messageType = "failure" , message -
    //좀더 생각해보기. http status code와 error-code는 다름.
    public static ResponseDto sendError(String message) {
        ResponseDto result = new ResponseDto();
        result.setCode(HttpStatus.OK.value());
        result.setMessageType("failure");
        result.setMessage(message);

        return result;
    }


    public static <T>ResponseDto sendData(T data) {
        ResponseDto result = new ResponseDto();
        result.setCode(HttpStatus.OK.value());
        result.setData(data);

        return result;
    }


    public static ResponseDto sendData(Map<String, ?> data) {
        ResponseDto result = new ResponseDto();
        result.setCode(HttpStatus.OK.value());
        result.setData(data);

        if (data.get("messageType") != null) {
            result.setMessageType(String.valueOf(data.get("messageType")));
        }
        if (data.get("message") != null) {
            result.setMessage(String.valueOf(data.get("message")));
        }

        return result;
    }

}
