package com.github.albertosh.adidas.backend.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.albertosh.adidas.backend.models.user.LoginInfo;
import com.github.albertosh.adidas.backend.usecases.auth.register.IRegisterUseCase;
import com.github.albertosh.adidas.backend.usecases.auth.register.RegisterUseCaseError;
import com.github.albertosh.adidas.backend.usecases.auth.register.RegisterUseCaseInput;
import com.github.albertosh.swagplash.annotations.ApiBodyParam;
import com.github.albertosh.swagplash.annotations.ApiOperation;
import com.github.albertosh.swagplash.annotations.ApiResponse;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import rx.Single;

import static com.github.albertosh.adidas.backend.utils.ObservableAndFutures.fromSingle;
import static com.github.albertosh.swagplash.annotations.ApiOperation.HttpMethod.POST;
import static play.libs.Json.toJson;

@Singleton
public class UserController extends Controller {

    private final IRegisterUseCase registerUseCase;

    @Inject
    public UserController(IRegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    @ApiOperation(httpMethod = POST, path = "/user")
    @ApiBodyParam(name = "email", required = true)
    @ApiBodyParam(name = "firstName", required = true)
    @ApiBodyParam(name = "lastName", required = true)
    @ApiBodyParam(name = "dateOfBirth", dataType = ApiBodyParam.DataType.DATE, required = true)
    @ApiBodyParam(name = "country", required = true)
    @ApiBodyParam(name = "language")
    @ApiResponse(code = CREATED, message = "User registered", response = LoginInfo.class)
    @ApiResponse(code = BAD_REQUEST, message = "Email is already registered")
    public CompletionStage<Result> register() {
        RegisterUseCaseInput input = new RegisterUseCaseInput.Builder()
                .email((String) ctx().args.get("email"))
                .firstName((String) ctx().args.get("firstName"))
                .lastName((String) ctx().args.get("lastName"))
                .dateOfBirth((LocalDate) ctx().args.get("dateOfBirth"))
                .country((String) ctx().args.get("country"))
                .preferredLanguage(((Optional<String>) ctx().args.get("language")).orElse(null))
                .build();
        return fromSingle(
                registerUseCase.execute(input)
                        .map(loginInfo -> ok(toJson(loginInfo)))
                        .onErrorResumeNext(error -> {
                            if (error == RegisterUseCaseError.emailIsAlreadyRegistered) {
                                return Single.just(badRequest(toJson(error)));
                            } else
                                return Single.error(error);
                        })
        );
    }

}
