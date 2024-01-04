package com.test.dto;

import lombok.Getter;
import lombok.Setter;

//@Getter
//public class AuthenticationResponse {
//
//    private String token;
//
//    public AuthenticationResponse(String token) {
//        this.token = token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//}
@Getter
@Setter
public class AuthenticationResponse {

    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
}