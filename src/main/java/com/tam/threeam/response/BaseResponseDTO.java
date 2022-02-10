package com.tam.threeam.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2022/02/07
 * @
 * @ 수정일         수정자                   수정내용
 * @ ———    ————    —————————————
 * @ 2022/02/07						최초 작성
 * @ 2022/02/09		전예지			success 파라미터 변경
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseDTO implements Serializable {
    private static final long serialVersionUID = 8801985488178486968L;

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    private String code = "SC001";
    private String message;
    private String messageType;
    private Object data;

    public static BaseResponseDTO success(UserResponseDto.TokenInfo tokenInfo) {
        return BaseResponseDTO.builder()
                .messageType(SUCCESS)
                .data(tokenInfo)
                .code("SC001")
                .build();
    }

    public static BaseResponseDTO success(Object result) {
        return BaseResponseDTO.builder()
                .messageType(SUCCESS)
                .data(result)
                .code("SC002")
                .build();
    }
    
    public static BaseResponseDTO success(String message) {
        return BaseResponseDTO.builder()
        		.message(message)
                .messageType(SUCCESS)
                .code("SC003")
                .build();
    }

    public static BaseResponseDTO success(UserResponseDto.myPageInfo mypageInfo) {
        return BaseResponseDTO.builder()
                .messageType(SUCCESS)
                .data(mypageInfo)
                .code("SC004")
                .build();
    }

    public static BaseResponseDTO success(UserResponseDto.orderPageInfo orderPageInfo) {
        return BaseResponseDTO.builder()
                .messageType(SUCCESS)
                .data(orderPageInfo)
                .code("SC004")
                .build();
    }

    public static BaseResponseDTO fail(String message) {
        return BaseResponseDTO.builder()
                .message(message)
                .messageType(FAIL)
                .code("ER001")
                .build();
    }
}
