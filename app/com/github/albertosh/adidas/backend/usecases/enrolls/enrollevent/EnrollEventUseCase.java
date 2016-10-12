package com.github.albertosh.adidas.backend.usecases.enrolls.enrollevent;

import com.github.albertosh.adidas.backend.models.event.Event;
import com.github.albertosh.adidas.backend.models.event.MultilingualEvent;
import com.github.albertosh.adidas.backend.models.user.User;
import com.github.albertosh.adidas.backend.persistence.core.PersistenceError;
import com.github.albertosh.adidas.backend.persistence.event.IMultilingualEventPersistenceRead;
import com.github.albertosh.adidas.backend.persistence.user.IUserPersistenceRead;
import com.github.albertosh.adidas.backend.persistence.user.IUserPersistenceUpdate;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Single;

@Singleton
public class EnrollEventUseCase
        implements IEnrollEventUseCase {

    private final IUserPersistenceRead userPersistenceRead;
    private final IUserPersistenceUpdate userPersistenceUpdate;
    private final IMultilingualEventPersistenceRead multilingualEventPersistenceRead;

    @Inject
    public EnrollEventUseCase(IUserPersistenceRead userPersistenceRead,
                              IUserPersistenceUpdate userPersistenceUpdate,
                              IMultilingualEventPersistenceRead multilingualEventPersistenceRead) {
        this.userPersistenceRead = userPersistenceRead;
        this.userPersistenceUpdate = userPersistenceUpdate;
        this.multilingualEventPersistenceRead = multilingualEventPersistenceRead;
    }

    @Override
    public Single<Event> execute(EnrollEventUseCaseInput input) {
        Observable<MultilingualEvent> obsEvent = multilingualEventPersistenceRead.read(input.getEventId())
                .onErrorResumeNext(error -> {
                    if (error == PersistenceError.itemNotFound) {
                        return Single.error(EnrollEventUseCaseError.eventDoesNotExist);
                    } else {
                        return Single.error(error);
                    }
                })
                .toObservable()
                .share();

        Observable<User> obsUser = userPersistenceRead.read(input.getUuid()).toObservable();

        Observable<User> obsUpdatedUser = Observable.zip(obsEvent, obsUser, (event, user) -> {
            User updatedUser = new User.Builder()
                    .fromPrototype(user)
                    .withEnrollment(event.getId())
                    .build();
            return userPersistenceUpdate.update(updatedUser).toObservable();
        }).flatMap(obs -> obs);

        return Observable.zip(obsEvent, obsUpdatedUser,
                (event, user) -> event.getLocalizedOrDefaultEvent(user.getPreferredLanguage()))
                .toSingle();
    }
}
