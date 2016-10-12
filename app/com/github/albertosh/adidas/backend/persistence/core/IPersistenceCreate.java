package com.github.albertosh.adidas.backend.persistence.core;

import rx.Single;

public interface IPersistenceCreate<T extends ObjectWithId> {

    public Single<T> create(ObjectWithId.Builder<T> builder);

}
