package com.github.albertosh.adidas.backend.persistence.core;

import rx.Single;

public interface IPersistenceUpdate<T extends ObjectWithId> {

    public Single<T> update(T item);

}
