package com.ecommerce.backend.service.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class EmailNotExists extends RuntimeException {


    public EmailNotExists() {
        super();
    }

    public EmailNotExists(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmailNotExists(final String message) {
        super(message);
    }

    public EmailNotExists(final Throwable cause) {
        super(cause);
    }

}