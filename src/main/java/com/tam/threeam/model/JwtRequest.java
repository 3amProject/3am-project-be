package com.tam.threeam.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String userId;

    private String password;

//    public JwtRequest(){}
//
//    public JwtRequest(String userId, String password){
//        this.setUserId(userId);
//        this.setPassword(password);
//    }

}
