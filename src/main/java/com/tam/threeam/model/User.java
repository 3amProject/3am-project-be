package com.tam.threeam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    //TODO 필드 체크 , mobileNum 타입 체크

    private int id;

    private String userId;

    private String username;

    private String password;

    private String email;

    private String address;

    private int mobileNum;


}
