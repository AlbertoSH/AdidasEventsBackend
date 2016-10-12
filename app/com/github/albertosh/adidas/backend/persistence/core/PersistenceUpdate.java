package com.github.albertosh.adidas.backend.persistence.core;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;

import org.bson.types.ObjectId;

import javax.inject.Named;

import rx.Single;
import rx.schedulers.Schedulers;

public abstract class PersistenceUpdate<T extends ObjectWithId>
        extends Persistence<T>
        implements IPersistenceUpdate<T> {

    public PersistenceUpdate(MongoClient client, @Named("dbName") String dbName) {
        super(client, dbName);
    }

    @Override
    public final Single<T> update(T item) {
        return getCollection()
                .findOneAndReplace(Filters.eq("_id", new ObjectId(item.getId())), item)
                .toSingle()
                .observeOn(Schedulers.io());
    }

}
