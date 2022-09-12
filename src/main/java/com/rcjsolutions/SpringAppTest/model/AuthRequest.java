package com.rcjsolutions.SpringAppTest.model;

import lombok.Data;

@Data
public class AuthRequest {
    String email;
    String password;
    String code;
}
