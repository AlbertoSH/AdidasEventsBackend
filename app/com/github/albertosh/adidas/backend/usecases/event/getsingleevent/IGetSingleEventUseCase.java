package com.github.albertosh.adidas.backend.usecases.event.getsingleevent;

import com.google.inject.ImplementedBy;

import com.github.albertosh.adidas.backend.models.Event;
import com.github.albertosh.adidas.backend.usecases.IUseCase;

@ImplementedBy(GetSingleEventUseCase.class)
public interface IGetSingleEventUseCase
        extends IUseCase<GetSingleEventUseCaseInput, Event> {
}
