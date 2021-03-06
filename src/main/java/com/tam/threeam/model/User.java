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

    private int id;

    private String userId;

    private String password;
    
    private String name;

    private String phoneNum;
    
    private String address;
    
    private String email;

    private String refreshToken;

}
