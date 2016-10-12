package com.github.albertosh.adidas.backend.usecases.event.getsingleevent;

import com.github.albertosh.adidas.backend.models.Event;
import com.github.albertosh.adidas.backend.persistence.event.IMultilingualEventPersistenceRead;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;

@Singleton
public class GetSingleEventUseCase
        implements IGetSingleEventUseCase {

    private final IMultilingualEventPersistenceRead multilingualEventPersistenceRead;

    @Inject
    public GetSingleEventUseCase(IMultilingualEventPersistenceRead multilingualEventPersistenceRead) {
        this.multilingualEventPersistenceRead = multilingualEventPersistenceRead;
    }

    @Override
    public Single<Event> execute(GetSingleEventUseCaseInput input) {
        return multilingualEventPersistenceRead.read(input.getId())
                .map(multilingualEvent -> input.getLanguage()
                        .map(multilingualEvent::getLocalizedOrDefaultEvent)
                        .orElseGet(multilingualEvent::getDefaultLanguageEvent));
    }

}
