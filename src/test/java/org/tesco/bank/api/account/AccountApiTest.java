package org.tesco.bank.api.account;

import com.jayway.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.tesco.bank.api.account.api.exception.UnauthorizedException;
import org.tesco.bank.api.account.api.request.AccountCreationRequest;
import org.tesco.bank.api.account.domain.AccountID;
import org.tesco.bank.api.account.domain.AccountType;
import org.tesco.bank.api.account.service.AccountService;
import org.tesco.bank.api.account.service.IdentityService;

import static com.jayway.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@SpringBootTest(classes = AccountApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("accountAPITestProfile")
public class AccountApiTest {

    private static final String ACCOUNT_ID = "12345678";
    private static final String CUSTOMER_ID = "12345678910112";
    private static final String CUSTOMER_AUTH_TOKEN = "someobscuretoken";
    public static final String INVALID_CUSTOMER_AUTH_TOKEN = "someoinvalidbscuretoken";

    @Autowired
    AccountService accountService;

    @Autowired
    IdentityService identityService;

    @BeforeClass
    public static void fixtureSetup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void must_report_api_status() {
        given().when().get("/_status").then()
                .statusCode(SC_OK)
                .body("status", is("Ok"));
    }

    @Test
    public void must_return_bad_request_when_an_empty_account_creation_request_is_received() {
        String customerAuthToken = "someobscuretoken";
        when(identityService.validateToken(customerAuthToken)).thenReturn(true);
        given().when().contentType("application/json").header("Authorization-X", "customer-auth-token=" + customerAuthToken).post("/account/create").then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void must_return_unauthorized_when_an_invalid_customer_auth_token_is_received() {
        String invalidCustomerAuthToken = INVALID_CUSTOMER_AUTH_TOKEN;
        AccountCreationRequest accountCreationRequest = new AccountCreationRequest(AccountType.SMART_SAVE, CUSTOMER_ID);
        when(identityService.validateToken(invalidCustomerAuthToken)).thenThrow(new UnauthorizedException("Invalid customer auth token"));
        given().when().contentType("application/json").header("Authorization-X", "customer-auth-token=" + invalidCustomerAuthToken).body(accountCreationRequest).post("/account/create").then()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Test
    public void must_return_a_new_smart_save_account_with_an_id_when_a_valid_account_creation_request_is_received() {
        AccountID accountIdToReturn = new AccountID(ACCOUNT_ID);
        String customerId = CUSTOMER_ID;
        String customerAuthToken = CUSTOMER_AUTH_TOKEN;

        when(identityService.validateToken(customerAuthToken)).thenReturn(true);
        when(accountService.createAccount(customerId)).thenReturn(accountIdToReturn);

        AccountCreationRequest accountCreationRequest = new AccountCreationRequest(AccountType.SMART_SAVE, customerId);

        given().when().contentType("application/json").header("Authorization-X", "customer-auth-token=" + customerAuthToken).body(accountCreationRequest).post("/account/create").then()
                .statusCode(SC_OK)
                .body("accountId", is(accountIdToReturn.getAccountId()));

        verify(identityService, times(1)).validateToken(customerAuthToken);
        verify(accountService, times(1)).createAccount(customerId);

    }


}
