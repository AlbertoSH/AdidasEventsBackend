package com.github.albertosh.adidas.backend.usecases.event.getEvents;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.Event;
import com.github.albertosh.adidas.backend.usecases.IUseCase;

import java.util.List;

@ImplementedBy(GetEventsUseCase.class)
public interface IGetEventsUseCase
        extends IUseCase<GetEventsUseCaseInput, List<Event>> {
}
