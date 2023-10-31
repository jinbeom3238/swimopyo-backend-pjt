package com.btc.swimpyo.backend.utils.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    UNKNOWN_ERROR(401,"UNAUTHORIZED","인증 토큰이 존재하지 않습니다."),
    WRONG_TYPE_TOKEN(401,"UNAUTHORIZED","잘못된 토큰 정보입니다."),
    EXPIRED_TOKEN(401,"UNAUTHORIZED","만료된 토큰 정보입니다."),
    UNSUPPORTED_TOKEN(401,"UNAUTHORIZED","지원하지 않는 토큰 방식입니다."),
    ACCESS_DENIED(401,"UNAUTHORIZED","알 수 없는 이유로 요청이 거절되었습니다."),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_INPUT_VALUE(400,"BAD_REQUEST", "입력값이 올바르지 않습니다."),
    BAD_REQUEST(400, "BAD_REQUEST","잘못된 요청입니다."),
    DB_ERROR(400,"BAD_REQUEST","DB에러 발생했습니다."),
    NULL_EXCEPTION(400,"BAD_REQUEST","Null 예외 발생");

    private int status;
    private final String code;
    private final String message;




//    private int code;
//    private String message;
//
//    ErrorMessage(int code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public String getMsg() {
//        return message;
//    }

}
