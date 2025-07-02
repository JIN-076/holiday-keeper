package com.planitsquare.recruitment.exception.feign;

import static com.planitsquare.recruitment.exception.code.ErrorCode400.PATH_PARAMETER_BAD_REQUEST;
import static com.planitsquare.recruitment.exception.code.ErrorCode404.ENDPOINT_NOT_FOUND;
import static com.planitsquare.recruitment.exception.code.ErrorCode500.INTERNAL_SERVER_ERROR;

import com.planitsquare.recruitment.exception.feign.external.BadRequestException;
import com.planitsquare.recruitment.exception.feign.external.ExternalServiceException;
import com.planitsquare.recruitment.exception.feign.external.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder delegate;

    @Override
    public Exception decode(String s, Response response) {
        int status = response.status();

        return switch (status) {
            case 400 -> new BadRequestException(PATH_PARAMETER_BAD_REQUEST);
            case 404 -> new ResourceNotFoundException(ENDPOINT_NOT_FOUND);
            default -> {
                if (status >= 500 && status < 600) {
                    yield new ExternalServiceException(INTERNAL_SERVER_ERROR);
                }
                yield delegate.decode(s, response);
            }
        };

    }
}
