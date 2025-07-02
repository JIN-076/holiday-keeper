package com.planitsquare.recruitment.exception;

import com.planitsquare.recruitment.exception.base.CustomException;
import com.planitsquare.recruitment.exception.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.step.FatalStepExecutionException;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import static com.planitsquare.recruitment.exception.code.ErrorCode400.ILLEGAL_INPUT_ARG;
import static com.planitsquare.recruitment.exception.code.ErrorCode400.PATH_PARAMETER_BAD_REQUEST;
import static com.planitsquare.recruitment.exception.code.ErrorCode500.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        log.info("Custom Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        return ErrorResponse.from(e.getErrorCode());
    }

    @ExceptionHandler(value = {
            BindException.class,
            MethodArgumentNotValidException.class
    })
    protected ResponseEntity<ErrorResponse> validationException(BindException e, HttpServletRequest request) {
        log.info("Validation Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
            builder.append(", ");
        }
        log.info(builder.toString());

        return ErrorResponse.ofWithErrorMessage(ILLEGAL_INPUT_ARG, builder.toString());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException e, HttpServletRequest request
    ) {
        log.info("IllegalArgumentException: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        return ErrorResponse.ofWithErrorMessage(ILLEGAL_INPUT_ARG, e.getMessage());
    }

    @ExceptionHandler(value = {
            MissingPathVariableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> missingPathVariableException(
            Exception e, HttpServletRequest request
    ) {
        log.info("Missing Path Variable Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        return ErrorResponse.from(PATH_PARAMETER_BAD_REQUEST);
    }

    @ExceptionHandler(value = {
        SkipLimitExceededException.class,
        JobExecutionException.class,
        FatalStepExecutionException.class,
        UnexpectedJobExecutionException.class}
    )
    protected ResponseEntity<ErrorResponse> handleJobExecutitonException(
        Exception e, HttpServletRequest request
    ) {
        log.info("Batch Job Execution Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        return ErrorResponse.from(INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e, HttpServletRequest request
    ) {
        log.info("Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        return ErrorResponse.from(INTERNAL_SERVER_ERROR);
    }

    private String getRequestURLWithQuery(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(request.getRequestURI());
        return request.getQueryString() != null ? url.append("?").append(request.getQueryString()).toString() : url.toString();
    }
}
