package com.github.albertosh.adidas.backend.utils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import rx.Single;

public class ObservableAndFutures {

    public static <T> CompletionStage<T> fromSingle(Single<T> single) {
        CompletableFuture<T> future = new CompletableFuture<T>();
        single.subscribe(
                future::complete,
                future::completeExceptionally);
        return future;
    }

}
