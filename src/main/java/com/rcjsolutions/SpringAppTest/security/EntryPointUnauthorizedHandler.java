package com.rcjsolutions.SpringAppTest.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcjsolutions.SpringAppTest.model.response.RestError;
import com.rcjsolutions.SpringAppTest.model.response.RestResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        RestResponse res = new RestResponse(false);
        res.setError(new RestError(401,"Access Denied"));
        httpServletResponse.getWriter().append(objectMapper.writeValueAsString(res));
        httpServletResponse.setHeader("Content-Type","application/json");
        httpServletResponse.setStatus(200);
    }
}
