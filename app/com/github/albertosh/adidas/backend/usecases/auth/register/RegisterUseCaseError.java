package com.github.albertosh.adidas.backend.usecases.auth.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"stackTrace", "localizedMessage"})
public class RegisterUseCaseError extends Throwable {

    public final static RegisterUseCaseError emailIsAlreadyRegistered =
            new RegisterUseCaseError(-1, "Email is already registered");
    @JsonProperty
    private final int code;

    private RegisterUseCaseError(int code, String message) {
        super(message);
        this.code = code;
    }

}
