package org.retail.bank.api.account.api.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class StatusHandler {
    public final Mono<ServerResponse> doStatus() {
        return ok().contentType(MediaType.APPLICATION_JSON).body(Mono.just("{\"status\": \"Ok\"}"), String.class);
    }
}
