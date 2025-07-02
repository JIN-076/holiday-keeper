package com.planitsquare.recruitment.domain.exception;

import com.planitsquare.recruitment.exception.base.CustomException;
import com.planitsquare.recruitment.exception.code.ErrorCode;

public class HolidayException extends CustomException {

    public HolidayException(ErrorCode errorCode) {
        super(errorCode);
    }
}
