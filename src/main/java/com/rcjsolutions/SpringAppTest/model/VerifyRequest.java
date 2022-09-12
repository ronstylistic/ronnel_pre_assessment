package com.rcjsolutions.SpringAppTest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rcjsolutions.SpringAppTest.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class VerifyRequest {
    private String code;
    private User user;
}
