package org.tesco.bank.api.account.api.request.validation;

import org.tesco.bank.api.account.api.exception.BadRequestException;

public interface ValidateableRequest {
    boolean isValidRequest() throws BadRequestException;
}
