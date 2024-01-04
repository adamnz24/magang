package com.test.dto;

import lombok.Getter;
import lombok.Setter;

//@Getter
//public class RegisterRequest {
//
//    private String firstname;
//    private String lastname;
//    private String email;
//    private String username;
//    private String password;
//
//
//
//    public RegisterRequest(String firstname, String lastname, String email, String username, String password) {
//        this.firstname = firstname;
//        this.lastname = lastname;
//        this.email = email;
//        this.username = username;
//        this.password = password;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//}
@Getter
@Setter


public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;


    public RegisterRequest(String firstname, String lastname, String email, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}