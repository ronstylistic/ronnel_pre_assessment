package com.rcjsolutions.SpringAppTest.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserData {
    private String id;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthday;
    private Integer age;
    private String position;
}
