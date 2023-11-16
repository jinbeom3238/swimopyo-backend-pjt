package com.btc.swimpyo.backend.config;

import com.btc.swimpyo.backend.utils.jwt.entity.ErrorCode;
import com.btc.swimpyo.backend.utils.jwt.entity.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLSyntaxErrorException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleException(NullPointerException e) {

        log.info("getMessage {}" ,e.getMessage());
        ErrorResponse response = new ErrorResponse(ErrorCode.NULL_EXCEPTION);
        return ResponseEntity.status(response.getStatus()).body(response);

    }

    @ExceptionHandler(value = BadSqlGrammarException.class)
    public ResponseEntity<ErrorResponse> handleException(BadSqlGrammarException e) {

        log.info("getMessage {}" ,e.getMessage());
        ErrorResponse response = new ErrorResponse(ErrorCode.DB_ERROR);
        return ResponseEntity.status(response.getStatus()).body(response);

    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {

        log.info("getMessage {}" ,e.getMessage());
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST);
        return ResponseEntity.status(response.getStatus()).body(response);

    }


}

