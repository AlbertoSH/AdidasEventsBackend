package com.github.albertosh.adidas.backend.security;

import com.github.albertosh.swagplash.authorization.AuthorizationCheck;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.inject.Named;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.unauthorized;

public class AdminAuthorizationCheck implements AuthorizationCheck {

    private final String authorizationHeader;
    private final String authorizationValue;

    @Inject
    public AdminAuthorizationCheck(@Named("authorizationHeader") String authorizationHeader,
                                   @Named("authorizationValue") String authorizationValue) {
        this.authorizationHeader = authorizationHeader;
        this.authorizationValue = authorizationValue;
    }

    @Override
    public CompletionStage<Result> doCheck(Http.Context ctx, Action<?> delegate) {
        Optional<String[]> tokenList = Optional.ofNullable(ctx.request().headers().get(authorizationHeader));
        if (tokenList.isPresent()) {
            if (tokenList.get().length > 1) {
                return CompletableFuture.completedFuture(badRequest());
            }

            String headerValue = tokenList.get()[0];
            if (authorizationValue.equals(headerValue)) {
                return delegate.call(ctx);
            } else {
                return CompletableFuture.completedFuture(unauthorized());
            }

        } else {
            return CompletableFuture.completedFuture(unauthorized());
        }
    }
}
