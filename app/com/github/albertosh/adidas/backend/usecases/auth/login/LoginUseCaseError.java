package com.github.albertosh.adidas.backend.usecases.auth.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"stackTrace", "localizedMessage"})
public class LoginUseCaseError extends Throwable {

    public final static LoginUseCaseError incorrectUserOrPassword =
            new LoginUseCaseError(-1, "Incorrect user or password");
    @JsonProperty
    private final int code;

    private LoginUseCaseError(int code, String message) {
        super(message);
        this.code = code;
    }

}
