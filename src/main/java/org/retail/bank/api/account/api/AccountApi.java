package org.retail.bank.api.account.api;

import org.retail.bank.api.account.api.exception.PathNotFoundException;
import org.retail.bank.api.account.api.handlers.AccountHandler;
import org.retail.bank.api.account.api.handlers.ErrorHandler;
import org.retail.bank.api.account.api.handlers.StatusHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.ALL;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountApi {

    @Autowired
    AccountHandler accountHandler;

    @Autowired
    ErrorHandler errorHandler;

    @Autowired
    StatusHandler statusHandler;
    
    @Bean
    public RouterFunction<?> routes() {
        return
                route(RequestPredicates.GET("/account/{id}/balance"),
                        request -> this.accountHandler.getBalance(request))
                        .and(
                                route(RequestPredicates.POST("/account/create").and(RequestPredicates.accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON))),
                                        request -> this.accountHandler.createAccount(request)))
                        .and(
                                route(RequestPredicates.GET("/account/{id}").and(RequestPredicates.accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON))),
                                        request -> this.accountHandler.getAccount(request)))
                        .and(
                                route(RequestPredicates.POST("/account/{id}/deposit").and(RequestPredicates.accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON))),
                                        request -> this.accountHandler.doDeposit(request)))
                        .and(
                                route(RequestPredicates.POST("/account/{id}/credit").and(RequestPredicates.accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON))),
                                        request -> this.accountHandler.doWithdraw(request)))
                        .and(
                                route(RequestPredicates.GET("/_status").and(contentType(ALL)),
                                        request -> this.statusHandler.doStatus()))
                        .andOther(
                                route(RequestPredicates.all(), request -> this.errorHandler.handleError(new PathNotFoundException("not found"))));
    }
}
