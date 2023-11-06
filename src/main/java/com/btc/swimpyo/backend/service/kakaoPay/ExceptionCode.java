package com.btc.swimpyo.backend.service.kakaoPay;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    PAY_CANCEL("1001", "PAY CANCEL"),
    PAY_FAILED("1002", "PAY FAILED"),
    ;

    private final String code;
    private final String message;

    ExceptionCode(final String code, final String message) {
        this.code = code;
        this.message = message;

    }

}
