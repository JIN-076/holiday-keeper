package com.planitsquare.recruitment.domain.exception;

import com.planitsquare.recruitment.exception.base.CustomException;
import com.planitsquare.recruitment.exception.code.ErrorCode;

public class CountryException extends CustomException {

    public CountryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
