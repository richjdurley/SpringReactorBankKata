package org.retail.bank.api.account.api.exception;

import org.retail.bank.api.account.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ExceptionToWebTransformer {

    private final HttpStatus httpStatus;
    private final String message;

    private ExceptionToWebTransformer(final Throwable throwable) {
        this.httpStatus = getHttpStatus(throwable);
        this.message = throwable.getMessage();
    }

    public static ExceptionToWebTransformer from(final Throwable throwable) {
        return new ExceptionToWebTransformer(throwable);
    }

    public static <T extends Throwable> Mono<ExceptionToWebTransformer> transform(final Mono<T> throwable) {
        return throwable.flatMap(error -> Mono.just(new ExceptionToWebTransformer(error)));
    }

    private HttpStatus getHttpStatus(final Throwable error) {
        if (error instanceof BadRequestException) {
            return HttpStatus.BAD_REQUEST;
        } else if (error instanceof PathNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (error instanceof UnauthorizedException) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public static <T extends Throwable> Mono<ServerResponse> transformToServerResponse(final Mono<T> monoError) {
        return monoError.transform(ExceptionToWebTransformer::transform)
                .flatMap(translation -> ServerResponse
                        .status(translation.getHttpStatus()).contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(new ErrorResponse(translation.getHttpStatus(), translation.getMessage())), ErrorResponse.class));
    }

}
