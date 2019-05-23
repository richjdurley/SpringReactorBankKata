package org.tesco.bank.api.account;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.DirtiesContext;
import org.tesco.bank.api.account.service.AccountService;
import org.tesco.bank.api.account.service.IdentityService;

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
