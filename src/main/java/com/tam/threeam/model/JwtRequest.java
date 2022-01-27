package com.tam.threeam.model;

import lombok.Data;

@Data
public class JwtRequest {

    private String userId;

    private String password;

}
