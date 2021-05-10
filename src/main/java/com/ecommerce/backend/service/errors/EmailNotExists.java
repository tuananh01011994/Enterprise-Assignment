package com.ecommerce.backend.service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class UserNotExistException extends RuntimeException {


    public UserNotExistException() {
        super();
    }

    public UserNotExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotExistException(final String message) {
        super(message);
    }

    public UserNotExistException(final Throwable cause) {
        super(cause);
    }

}