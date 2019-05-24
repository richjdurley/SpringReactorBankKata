package org.retail.bank.api.account.api.handlers;

import org.retail.bank.api.account.api.exception.BadRequestException;
import org.retail.bank.api.account.api.request.AccountCreationRequest;
import org.retail.bank.api.account.domain.AccountID;
import org.retail.bank.api.account.service.AccountService;
import org.retail.bank.api.account.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.retail.bank.api.account.api.handlers.utils.HandlerUtils.extractAuthorizationTokenFromHeader;

@Component
public class AccountHandler {

    @Autowired
    AccountService accountService;

    @Autowired
    IdentityService identityService;

    @Autowired
    ErrorHandler errorHandler;

    public Mono<ServerResponse> createAccount(ServerRequest request) {
        return request.bodyToMono(AccountCreationRequest.class).
                filter(a -> identityService.validateToken(extractAuthorizationTokenFromHeader(request))).
                filter(AccountCreationRequest::isValidRequest).
                flatMap(i -> ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just(accountService.createAccount(i.getCustomerId())), AccountID.class)).
                switchIfEmpty(errorHandler.handleError(BadRequestException.EMPTY_REQUEST_BODY)).
                onErrorResume(e -> errorHandler.handleError(e));
    }

    public Mono<ServerResponse> getAccount(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> doDeposit(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> doWithdraw(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> getBalance(ServerRequest request) {
        return null;
    }


}
