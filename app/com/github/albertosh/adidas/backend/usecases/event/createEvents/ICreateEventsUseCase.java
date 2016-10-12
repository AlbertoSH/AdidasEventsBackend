package com.github.albertosh.adidas.backend.usecases.event.createEvents;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.Event;
import com.github.albertosh.adidas.backend.usecases.IUseCase;

@ImplementedBy(CreateEventUseCase.class)
public interface ICreateEventsUseCase
        extends IUseCase<CreateEventUseCaseInput, Event> {
}
