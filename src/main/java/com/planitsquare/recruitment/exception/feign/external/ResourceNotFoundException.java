package com.planitsquare.recruitment.exception.feign.external;

import com.planitsquare.recruitment.exception.base.CustomException;
import com.planitsquare.recruitment.exception.code.ErrorCode;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
