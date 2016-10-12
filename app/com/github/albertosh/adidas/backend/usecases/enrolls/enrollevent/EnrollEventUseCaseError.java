package com.github.albertosh.adidas.backend.usecases.enrolls.enrollevent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.albertosh.adidas.backend.usecases.auth.register.RegisterUseCaseError;

@JsonIgnoreProperties({"stackTrace", "localizedMessage"})
public class EnrollEventUseCaseError extends Throwable {

    @JsonProperty
    private final int code;

    public final static EnrollEventUseCaseError eventDoesNotExist =
            new EnrollEventUseCaseError(-1, "Event does not exist");

    private EnrollEventUseCaseError(int code, String message) {
        super(message);
        this.code = code;
    }

}
