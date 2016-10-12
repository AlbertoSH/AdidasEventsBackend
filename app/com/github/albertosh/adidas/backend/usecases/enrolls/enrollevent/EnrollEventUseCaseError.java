package com.github.albertosh.adidas.backend.usecases.enrolls.enrollevent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"stackTrace", "localizedMessage"})
public class EnrollEventUseCaseError extends Throwable {

    public final static EnrollEventUseCaseError eventDoesNotExist =
            new EnrollEventUseCaseError(-1, "Event does not exist");
    @JsonProperty
    private final int code;

    private EnrollEventUseCaseError(int code, String message) {
        super(message);
        this.code = code;
    }

}
