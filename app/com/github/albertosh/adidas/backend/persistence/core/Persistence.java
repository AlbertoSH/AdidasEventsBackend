package com.github.albertosh.adidas.backend.persistence.core;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoCollection;

import javax.inject.Named;

public abstract class Persistence<T> {

    protected final MongoClient client;
    protected final String dbName;

    public Persistence(MongoClient client, @Named("dbName") String dbName) {
        this.client = client;
        this.dbName = dbName;
    }

    protected final MongoCollection<T> getCollection() {
        return client.getDatabase(dbName)
                .getCollection(getCollectionName())
                .withDocumentClass(getItemsClass());
    }

    protected abstract Class<T> getItemsClass();

    protected abstract String getCollectionName();
}
