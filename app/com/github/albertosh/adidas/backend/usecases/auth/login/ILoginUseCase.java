package com.github.albertosh.adidas.backend.usecases.auth.login;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.user.LoginInfo;
import com.github.albertosh.adidas.backend.usecases.IUseCase;

@ImplementedBy(LoginUseCase.class)
public interface ILoginUseCase
        extends IUseCase<LoginUseCaseInput, LoginInfo> {
}
