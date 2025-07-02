package com.planitsquare.recruitment.exception.feign.external;

import com.planitsquare.recruitment.exception.base.CustomException;
import com.planitsquare.recruitment.exception.code.ErrorCode;

public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
