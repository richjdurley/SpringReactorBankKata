package org.retail.bank.api.account;

import org.mockito.Mockito;
import org.retail.bank.api.account.service.AccountService;
import org.retail.bank.api.account.service.IdentityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;

@Profile("accountAPITestProfile")
@Configuration
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
public class AccountAPITestProfile {

    @Bean
    @Primary
    public AccountService mockedAccountService() {
        return Mockito.mock(AccountService.class);
    }

    @Bean
    @Primary
    public IdentityService mockedIdentityService() {
        return Mockito.mock(IdentityService.class);
    }

}
