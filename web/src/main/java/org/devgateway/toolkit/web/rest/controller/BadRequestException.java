package org.devgateway.toolkit.web.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Daniel Oliva
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request, please check your request")
public class BadRequestException extends RuntimeException {

    public BadRequestException(final String message, final Exception e) {
        super(message, e);
    }

    public BadRequestException(final String message) {
        super(message);
    }
}
