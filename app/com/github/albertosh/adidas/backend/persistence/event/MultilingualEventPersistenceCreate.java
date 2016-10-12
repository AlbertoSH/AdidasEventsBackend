package com.github.albertosh.adidas.backend.persistence.event;

import com.github.albertosh.adidas.backend.models.MultilingualEvent;
import com.github.albertosh.adidas.backend.persistence.core.PersistenceCreate;
import com.github.albertosh.adidas.backend.persistence.core.PersistenceRead;
import com.mongodb.rx.client.MongoClient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class MultilingualEventPersistenceCreate
        extends PersistenceCreate<MultilingualEvent>
        implements IMultilingualEventPersistenceCreate {

    @Inject
    public MultilingualEventPersistenceCreate(MongoClient client, @Named("dbName") String dbName) {
        super(client, dbName);
    }

    @Override
    protected Class<MultilingualEvent> getItemsClass() {
        return MultilingualEvent.class;
    }

    @Override
    protected String getCollectionName() {
        return "event";
    }
}
