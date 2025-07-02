package com.planitsquare.recruitment.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode404 implements ErrorCode {

    ENDPOINT_NOT_FOUND("E000301", "요청하신 리소스를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private final String code;
    private final String message;

    ErrorCode404(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
