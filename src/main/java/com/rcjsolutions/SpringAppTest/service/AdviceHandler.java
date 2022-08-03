/*
 * Copyright (c) 2019
 *    Reliant Business Valuation, LLC - http://reliantvalue.com
 *    Author: Xaero Zero - xaerozero@icloud.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rcjsolutions.SpringAppTest.service;


import com.rcjsolutions.SpringAppTest.model.response.RestError;
import com.rcjsolutions.SpringAppTest.model.response.RestResponse;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.security.auth.login.LoginException;
import java.io.IOException;

@ControllerAdvice(basePackages = "com.rcjsolutions.SpringAppTest")
public class AdviceHandler implements ResponseBodyAdvice<Object> {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AdviceHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResponse genericExceptionHandler(Exception e) {
        logger.error("Exception: ",e);
        e.printStackTrace();
        RestResponse res = new RestResponse(false);
        res.setError(new RestError(400, e.getMessage()));
        return res;
    }

    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResponse ioException(Exception e) {
        logger.error("IOException: ",e);
        e.printStackTrace();
        RestResponse res = new RestResponse(false);
        res.setError(new RestError(3, e.getMessage()));
        return res;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        //noinspection ConditionCoveredByFurtherCondition
        if(o instanceof ByteArrayResource || o instanceof ResponseEntity || o instanceof Resource)
            return o;
        else return o instanceof RestResponse ? o : new RestResponse(o);
    }
}
