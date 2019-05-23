package org.tesco.bank.api.account.api.handlers.utils;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.tesco.bank.api.account.api.exception.BadRequestException;

public class HandlerUtils {

    private static final String AUTHORIZATION_X = "Authorization-X";
    private static final String CUSTOMER_AUTH_TOKEN_HEADER_VALUE = "customer-auth-token";

    public static String extractAuthorizationTokenFromHeader(ServerRequest request) {
        String authTokenValue = "";
        if (request.headers().header(AUTHORIZATION_X).isEmpty())
            throw new BadRequestException("X-Authorization header is missing");
        else {
            String[] authHeader = request.headers().header(AUTHORIZATION_X).get(0).split(";");
            for (String value : authHeader) {
                String[] keyValue = value.split("=");
                if (keyValue[0].equals(CUSTOMER_AUTH_TOKEN_HEADER_VALUE))
                    authTokenValue = keyValue[1];
            }
        }
        return authTokenValue;
    }
}
