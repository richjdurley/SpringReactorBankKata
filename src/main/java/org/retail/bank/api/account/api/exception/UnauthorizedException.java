package org.retail.bank.api.account.api.exception;

public class UnauthorizedException extends RuntimeException  {
    public UnauthorizedException(String message) {
        super(message);
    }
}
