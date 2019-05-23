package org.retail.bank.api.account.service;

import org.retail.bank.api.account.api.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

@Component
public class IdentityService {
    public boolean validateToken(String identityToken) throws UnauthorizedException {
        return false;
    }
}
