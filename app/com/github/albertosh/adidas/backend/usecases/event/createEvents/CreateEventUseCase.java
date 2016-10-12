package com.github.albertosh.adidas.backend.usecases.event.createEvents;

import com.github.albertosh.adidas.backend.models.event.Event;
import com.github.albertosh.adidas.backend.models.event.EventTexts;
import com.github.albertosh.adidas.backend.models.event.MultilingualEvent;
import com.github.albertosh.adidas.backend.persistence.event.IMultilingualEventPersistenceCreate;
import com.github.albertosh.adidas.backend.persistence.utils.IdGenerator;
import com.github.albertosh.adidas.backend.usecases.utils.storeimage.IStoreImageUseCase;
import com.github.albertosh.adidas.backend.usecases.utils.storeimage.StoreImageUseCaseInput;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Single;

@Singleton
public class CreateEventUseCase
        implements ICreateEventsUseCase {

    private final IdGenerator idGenerator;
    private final IStoreImageUseCase storeImageUseCase;
    private final IMultilingualEventPersistenceCreate multilingualEventPersistenceCreate;
    private final String defaultLanguage;

    @Inject
    public CreateEventUseCase(IdGenerator idGenerator, IStoreImageUseCase storeImageUseCase,
                              IMultilingualEventPersistenceCreate multilingualEventPersistenceCreate,
                              @Named("defaultLanguage") String defaultLanguage) {
        this.idGenerator = idGenerator;
        this.storeImageUseCase = storeImageUseCase;
        this.multilingualEventPersistenceCreate = multilingualEventPersistenceCreate;
        this.defaultLanguage = defaultLanguage;
    }

    @Override
    public Single<Event> execute(CreateEventUseCaseInput input) {
        Single<String> imageId;
        if (input.getImage().isPresent()) {
            String logoId = idGenerator.getNewId();
            StoreImageUseCaseInput storeInput = new StoreImageUseCaseInput.Builder()
                    .image(input.getImage().get())
                    .imageId(logoId)
                    .build();
            imageId = storeImageUseCase.execute(storeInput);
        } else {
            imageId = Single.just(null);
        }

        return imageId.flatMap(id -> {
            MultilingualEvent.Builder builder = new MultilingualEvent.Builder()
                    .date(input.getDate())
                    .imageId(id);

            if (id == null)
                // TODO check that the url points to a valid image
                builder.imageUrl(input.getImageUrl().orElse(null));

            builder.defaultLanguage(input.getDefaultLanguage()
                            .orElse(defaultLanguage),
                    new EventTexts.Builder()
                            .title(input.getTitle())
                            .description(input.getDescription().orElse(null))
                            .build());

            return multilingualEventPersistenceCreate.create(builder);
        }).map(MultilingualEvent::getDefaultLanguageEvent);


    }

}
