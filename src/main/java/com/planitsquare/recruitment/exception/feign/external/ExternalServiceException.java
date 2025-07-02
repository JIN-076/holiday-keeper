package com.planitsquare.recruitment.exception.feign.external;

import com.planitsquare.recruitment.exception.base.CustomException;
import com.planitsquare.recruitment.exception.code.ErrorCode;

public class ExternalServiceException extends CustomException {

    public ExternalServiceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
