package com.github.albertosh.adidas.backend.usecases.event.createEvents;

import com.github.albertosh.adidas.backend.models.Event;
import com.github.albertosh.adidas.backend.models.EventTexts;
import com.github.albertosh.adidas.backend.models.MultilingualEvent;
import com.github.albertosh.adidas.backend.persistence.core.IPersistenceCreate;
import com.github.albertosh.adidas.backend.persistence.event.IMultilingualEventPersistenceCreate;
import com.github.albertosh.adidas.backend.persistence.utils.IdGenerator;
import com.github.albertosh.adidas.backend.usecases.utils.storeimage.IStoreImageUseCase;
import com.github.albertosh.adidas.backend.usecases.utils.storeimage.StoreImageUseCase;
import com.github.albertosh.adidas.backend.usecases.utils.storeimage.StoreImageUseCaseInput;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Single;

@Singleton
public class CreateEventUseCase
        implements ICreateEventsUseCase {

    private final IdGenerator idGenerator;
    private final IStoreImageUseCase storeImageUseCase;
    private final IMultilingualEventPersistenceCreate multilingualEventPersistenceCreate;

    @Inject
    public CreateEventUseCase(IdGenerator idGenerator, IStoreImageUseCase storeImageUseCase, IMultilingualEventPersistenceCreate multilingualEventPersistenceCreate) {
        this.idGenerator = idGenerator;
        this.storeImageUseCase = storeImageUseCase;
        this.multilingualEventPersistenceCreate = multilingualEventPersistenceCreate;
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

            builder.defaultLanguage(input.getDefaultLanguage(),
                    new EventTexts.Builder()
                            .title(input.getTitle())
                            .description(input.getDescription().orElse(null))
                            .build());

            return multilingualEventPersistenceCreate.create(builder);
        }).map(MultilingualEvent::getDefaultLanguageEvent);


    }

}
