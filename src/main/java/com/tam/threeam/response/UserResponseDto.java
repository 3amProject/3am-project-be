package com.tam.threeam.response;

import com.tam.threeam.model.Cart;
import com.tam.threeam.model.Order;
import com.tam.threeam.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    public static class myPageInfo {
        public User userInfo;
        public List<Order> orderHistory;
    }


    @Builder
    @Data
    public static class orderPageInfo {
        public List<Cart> cartList;
        public User userInfo;
    }

}
