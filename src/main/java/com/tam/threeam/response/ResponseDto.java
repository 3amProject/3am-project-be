package com.tam.threeam.response;

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

    private int status;
    private Object data;
    private String messageType = "Success";
    private String message;


    public static <T>ResponseDto sendSuccess() {
        ResponseDto result = new ResponseDto();
        result.setStatus(HttpStatus.OK.value());

        return result;
    }

/*    public static <T>ResponseDto sendSuccess(T data) {
        ResponseDto result = new ResponseDto();
        result.setStatus(HttpStatus.OK.value());
        result.setData(data);

        return result;
    }*/



    public static <T>ResponseDto sendData(T data) {
        ResponseDto result = new ResponseDto();
        result.setStatus(HttpStatus.OK.value());
        result.setData(data);

        return result;
    }



/*    public static <T>ResponseDto sendMessage(T data) {
        ResponseDto result = new ResponseDto();
        result.setStatus(HttpStatus.OK.value());
        result.setData(data);

        return result;
    }*/



    public static ResponseDto sendMessage(Map<String, String> msgData) {
        ResponseDto result = new ResponseDto();
        result.setStatus(HttpStatus.OK.value());

        if (msgData.get("messageType") != null) {
            result.setMessageType(String.valueOf(msgData.get("messageType")));
        }
        if (msgData.get("message") != null) {
            result.setMessage(String.valueOf(msgData.get("message")));
        }

        return result;
    }


    public static <T> ResponseDto sendData(Map<String, String> msgData, T data) {
        ResponseDto result = new ResponseDto();
        result.setStatus(HttpStatus.OK.value());
        result.setData(data);

        if (msgData.get("messageType") != null) {
            result.setMessageType(String.valueOf(msgData.get("messageType")));
        }
        if (msgData.get("message") != null) {
            result.setMessage(String.valueOf(msgData.get("message")));
        }

        return result;
    }

}
