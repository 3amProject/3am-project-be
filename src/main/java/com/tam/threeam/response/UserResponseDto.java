package com.tam.threeam.response;

import lombok.Builder;
import lombok.Data;

public class UserResponseDto {

    @Builder
    @Data
    public static class TokenInfo {
        public String grantType;
        public String userId;
        public String accessToken;
        public String refreshToken;
        public Long refreshTokenExpirationTime;
    }
    
    @Builder
    @Data
    public static class MyPage{
    	
    	// 주문내역
    	
    	// User 정보
    }

    @Builder
    @Data
    public static class OrderPage{
    	
    	// 주문 상품 정보
    	
    	// User 정보
    }
    
}
