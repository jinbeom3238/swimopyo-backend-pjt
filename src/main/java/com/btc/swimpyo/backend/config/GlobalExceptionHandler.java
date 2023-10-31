package com.btc.swimpyo.backend.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = SQLSyntaxErrorException.class)
    public Map<String, Object> handleException(SQLSyntaxErrorException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("errMsg", e.getMessage());
        return map;
    }
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> handleException(Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("errMsg", e.getMessage());
        return map;
    }

}

