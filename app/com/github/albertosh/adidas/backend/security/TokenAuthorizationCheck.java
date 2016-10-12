package com.github.albertosh.adidas.backend.security;

import com.github.albertosh.adidas.backend.persistence.authinfo.AuthInfoFilterFields;
import com.github.albertosh.adidas.backend.persistence.authinfo.IAuthInfoPersistenceRead;
import com.github.albertosh.adidas.backend.persistence.utils.filter.Filter;
import com.github.albertosh.swagplash.authorization.AuthorizationCheck;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.inject.Named;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import rx.Single;

import static com.github.albertosh.adidas.backend.utils.ObservableAndFutures.fromSingle;
import static play.mvc.Results.badRequest;
import static play.mvc.Results.unauthorized;

public class TokenAuthorizationCheck implements AuthorizationCheck {

    private final String authorizationHeader;
    private final IAuthInfoPersistenceRead authInfoPersistenceRead;

    @Inject
    public TokenAuthorizationCheck(@Named("authorizationHeader") String authorizationHeader,
                                   IAuthInfoPersistenceRead authInfoPersistenceRead) {
        this.authorizationHeader = authorizationHeader;
        this.authInfoPersistenceRead = authInfoPersistenceRead;
    }

    @Override
    public CompletionStage<Result> doCheck(Http.Context ctx, Action<?> delegate) {
        Optional<String[]> tokenList = Optional.ofNullable(ctx.request().headers().get(authorizationHeader));
        if (tokenList.isPresent()) {
            if (tokenList.get().length > 1) {
                return CompletableFuture.completedFuture(badRequest());
            }

            String token = tokenList.get()[0];
            Filter filter = new Filter(AuthInfoFilterFields.TOKEN, token);
            return fromSingle(
                    authInfoPersistenceRead.read(filter)
                            .doOnNext(authInfo -> ctx.args.put("currentUUID", authInfo.getUuid()))
                            .toSingle()
                            .onErrorResumeNext(error -> Single.just(null))
            ).thenCompose(authInfo -> {
                if (authInfo != null)
                    return delegate.call(ctx);
                else
                    return CompletableFuture.completedFuture(unauthorized());
            });

        } else {
            return CompletableFuture.completedFuture(unauthorized());
        }
    }
}
