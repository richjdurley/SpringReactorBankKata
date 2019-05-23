package org.retail.bank.api.account;

import org.junit.Assert;
import org.junit.Test;
import org.retail.bank.api.account.api.exception.BadRequestException;
import org.retail.bank.api.account.domain.AccountType;
import org.retail.bank.api.account.api.request.AccountCreationRequest;

import static org.hamcrest.core.Is.is;

public class AccountCreationRequestTest {

    @Test
    public void must_return_true_when_valid_request_is_passed() {
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest(AccountType.SMART_SAVE, "123456778");
        Assert.assertThat(accountCreationRequest.isValidRequest(), is(true));
    }

    @Test(expected = BadRequestException.class)
    public void must_return_throw_exception_when_invalid_request_is_passed() {
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest();
        accountCreationRequest.isValidRequest();
    }


}
