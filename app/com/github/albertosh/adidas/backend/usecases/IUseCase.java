package com.github.albertosh.adidas.backend.usecases;

import rx.Single;

public interface IUseCase<I extends IUseCaseInput, D> {

    public Single<D> execute(I input);

}
