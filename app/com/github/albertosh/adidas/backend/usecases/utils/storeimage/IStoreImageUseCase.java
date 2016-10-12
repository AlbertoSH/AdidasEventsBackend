package com.github.albertosh.adidas.backend.usecases.utils.storeimage;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.usecases.IUseCase;

@ImplementedBy(StoreImageUseCase.class)
public interface IStoreImageUseCase
        extends IUseCase<StoreImageUseCaseInput, String> {
}
