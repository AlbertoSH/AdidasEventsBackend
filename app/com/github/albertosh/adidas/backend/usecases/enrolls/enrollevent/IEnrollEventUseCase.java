package com.github.albertosh.adidas.backend.usecases.enrolls.enrollevent;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.event.Event;
import com.github.albertosh.adidas.backend.usecases.IUseCase;

@ImplementedBy(EnrollEventUseCase.class)
public interface IEnrollEventUseCase
        extends IUseCase<EnrollEventUseCaseInput, Event> {
}
