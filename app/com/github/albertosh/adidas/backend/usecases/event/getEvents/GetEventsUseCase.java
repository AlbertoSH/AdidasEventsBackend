package com.github.albertosh.adidas.backend.usecases.event.getEvents;

import com.github.albertosh.adidas.backend.models.Event;
import com.github.albertosh.adidas.backend.persistence.event.IMultilingualEventPersistenceRead;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;

@Singleton
public class GetEventsUseCase
        implements IGetEventsUseCase {

    private final IMultilingualEventPersistenceRead multilingualEventPersistenceRead;

    @Inject
    public GetEventsUseCase(IMultilingualEventPersistenceRead multilingualEventPersistenceRead) {
        this.multilingualEventPersistenceRead = multilingualEventPersistenceRead;
    }

    @Override
    public Single<List<Event>> execute(GetEventsUseCaseInput input) {
        return multilingualEventPersistenceRead.read(
                input.getPage().orElse(0),
                input.getPageSize().orElse(null)
        )
                .map(multilingualEvent -> {
                    String language = input.getLanguage()
                            .orElse(multilingualEvent.getDefaultLanguage());
                    return multilingualEvent.getLocalizedEvent(language)
                            .orElseGet(multilingualEvent::getDefaultLanguageEvent);
                })
                .toList()
                .toSingle();
    }

}
