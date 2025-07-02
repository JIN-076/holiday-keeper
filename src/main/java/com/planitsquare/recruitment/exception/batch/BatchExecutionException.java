package com.planitsquare.recruitment.exception.batch;

import com.planitsquare.recruitment.exception.base.CustomException;
import com.planitsquare.recruitment.exception.code.ErrorCode;

public class BatchExecutionException extends CustomException {

    public BatchExecutionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
