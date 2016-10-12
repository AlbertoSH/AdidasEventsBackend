package com.github.albertosh.adidas.backend.persistence.core;

import com.github.albertosh.adidas.backend.persistence.utils.filter.Filter;
import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoCollection;

import org.bson.types.ObjectId;

import javax.inject.Named;

import rx.Single;

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
                .toSingle();
    }

}
