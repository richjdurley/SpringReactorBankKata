package org.tesco.bank.api.account.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.tesco.bank.api.account.api.exception.PathNotFoundException;
import org.tesco.bank.api.account.api.handlers.*;

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
                                route(RequestPredicates.GET("/account/{id}/statement").and(RequestPredicates.accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON))),
                                        request -> this.accountHandler.getStatement(request)))
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
