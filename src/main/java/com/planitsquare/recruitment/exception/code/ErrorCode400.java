package com.planitsquare.recruitment.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode400 implements ErrorCode {

    PATH_PARAMETER_BAD_REQUEST("E000001", "잘못된 경로 파라미터입니다."),
    ILLEGAL_INPUT_ARG("E000002", "유효하지 않은 입력 값입니다."),
    UNSUPPORTED_FILED_TYPE("E000003", "'지원하지 않는 필드 타입입니다."),
    ;

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private final String code;
    private final String message;

    ErrorCode400(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
