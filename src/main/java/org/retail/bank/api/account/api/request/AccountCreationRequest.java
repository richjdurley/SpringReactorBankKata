package org.retail.bank.api.account.api.request;

import org.retail.bank.api.account.api.exception.BadRequestException;
import org.retail.bank.api.account.domain.AccountType;
import org.retail.bank.api.account.api.request.validation.ValidateableRequest;

import java.util.ArrayList;
import java.util.List;

public class AccountCreationRequest implements ValidateableRequest {

    private AccountType accountType;
    private String customerId;

    public AccountCreationRequest() {
    }

    public AccountCreationRequest(AccountType accountType, String customerId) {
        this.accountType = accountType;
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public boolean isValidRequest() {
        List<String> errors = new ArrayList<>();
        if (customerId == null || customerId.trim().isEmpty())
            errors.add("Account creation request must specify a valid customerId");
        else if (accountType == null)
            errors.add("Account creation request must specify an account type");
        if (errors.size() > 0)
            throw new BadRequestException(errors);
        return true;
    }
}
