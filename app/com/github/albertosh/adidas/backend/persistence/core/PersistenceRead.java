package com.github.albertosh.adidas.backend.persistence.core;

import com.github.albertosh.adidas.backend.persistence.utils.filter.Filter;
import com.mongodb.rx.client.FindObservable;
import com.mongodb.rx.client.MongoClient;

import org.bson.types.ObjectId;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Named;

import rx.Observable;
import rx.Single;

import static com.mongodb.client.model.Filters.eq;

public abstract class PersistenceRead<T extends ObjectWithId>
        extends Persistence<T>
        implements IPersistenceRead<T> {

    public PersistenceRead(MongoClient client, @Named("dbName") String dbName) {
        super(client, dbName);
    }

    @Override
    public final Single<T> read(@Nonnull String id) {
        if (id == null)
            throw new IllegalArgumentException("ID can't be null");

        return getCollection()
                .find(eq("_id", new ObjectId(id)))
                .first()
                .flatMap(item -> {
                    if (item != null)
                        return Observable.just(item);
                    else
                        return Observable.error(PersistenceError.itemNotFound);
                })
                .toSingle();
    }

    @Override
    public Observable<T> read(@Nullable Filter filter, @Nullable Integer page, @Nullable Integer pageSize) {
        FindObservable<T> obs;
        if (filter != null)
            obs = getCollection()
                    .find(filter.getBsonFilter());
        else
            obs = getCollection().find();

        if ((page != null) && (pageSize != null)) {
            obs = obs
                    .skip(page * pageSize)
                    .limit(pageSize);
        }

        return obs.toObservable();
    }

}
