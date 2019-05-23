package org.tesco.bank.api.account.api.exception;

import java.util.List;

public class BadRequestException extends RuntimeException {

    public BadRequestException(List<String> messages) {
        super(String.join("\n", messages));
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public static final BadRequestException EMPTY_REQUEST_BODY = new BadRequestException("request body was empty");
}
