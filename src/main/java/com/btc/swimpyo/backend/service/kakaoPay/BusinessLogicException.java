package com.btc.swimpyo.backend.service.kakaoPay;



import lombok.Getter;

public class BusinessLogicException extends RuntimeException {

    @Getter
    private ExceptionCode exceptionCode;

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
