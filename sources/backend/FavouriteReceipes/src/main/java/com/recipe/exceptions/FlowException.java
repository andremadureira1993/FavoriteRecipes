package com.recipe.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.recipe.openapi.ErrorTypeEnum;

/**
 * Throw when any flow is interrupted by any reason.
 */
public class FlowException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlowException.class);

    private String errorDescription;
    private ErrorTypeEnum errorType;
    private HttpStatus httpStatus;

    public FlowException(String errorDescription, ErrorTypeEnum errorType, HttpStatus httpStatus) {
        this.errorDescription = errorDescription;
        this.errorType = errorType;
        this.httpStatus = httpStatus;

        LOGGER.error(errorDescription + ", http status: " + httpStatus + ", error type: " + errorType);
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public ErrorTypeEnum getErrorType() {
        return errorType;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return errorDescription;
    }
}
