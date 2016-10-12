package com.github.albertosh.adidas.backend.usecases;

import rx.Single;

public interface IUseCase<I, D> {

    public Single<D> execute(I input);

}
