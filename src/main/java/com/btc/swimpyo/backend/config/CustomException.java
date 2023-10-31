package com.btc.swimpyo.backend.config;

import com.btc.swimpyo.backend.utils.jwt.entity.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
}
