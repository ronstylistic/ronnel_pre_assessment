package com.rcjsolutions.SpringAppTest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {
    private String id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
}
