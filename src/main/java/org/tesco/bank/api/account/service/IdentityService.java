package org.tesco.bank.api.account.service;

import org.springframework.stereotype.Component;
import org.tesco.bank.api.account.api.exception.UnauthorizedException;

@Component
public class IdentityService {
    public boolean validateToken(String identityToken) throws UnauthorizedException {
        return false;
    }
}
