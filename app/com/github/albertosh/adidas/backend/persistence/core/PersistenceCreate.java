package com.github.albertosh.adidas.backend.persistence.core;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoCollection;

import org.bson.types.ObjectId;

import javax.inject.Named;

import rx.Single;

public abstract class PersistenceCreate<T extends ObjectWithId>
        extends Persistence<T>
        implements IPersistenceCreate<T> {

    public PersistenceCreate(MongoClient client, @Named("dbName") String dbName) {
        super(client, dbName);
    }

    @Override
    public final Single<T> create(ObjectWithId.Builder<T> builder) {

        if (builder.getId() == null) {
            builder.id(new ObjectId().toString());
        }

        T item = builder.build();
        MongoCollection<T> collection = getCollection();


        return collection.insertOne(item)
                .map(suc -> item)
                .toSingle();
    }

}
