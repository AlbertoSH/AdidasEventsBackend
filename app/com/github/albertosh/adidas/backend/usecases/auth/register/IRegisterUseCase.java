package com.github.albertosh.adidas.backend.usecases.auth.register;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.user.LoginInfo;
import com.github.albertosh.adidas.backend.usecases.IUseCase;

@ImplementedBy(RegisterUseCase.class)
public interface IRegisterUseCase
        extends IUseCase<RegisterUseCaseInput, LoginInfo> {
}
