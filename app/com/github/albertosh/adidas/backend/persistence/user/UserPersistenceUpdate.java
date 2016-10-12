package com.github.albertosh.adidas.backend.persistence.user;

import com.github.albertosh.adidas.backend.models.user.User;
import com.github.albertosh.adidas.backend.persistence.core.PersistenceUpdate;
import com.mongodb.rx.client.MongoClient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class UserPersistenceUpdate
        extends PersistenceUpdate<User>
        implements IUserPersistenceUpdate {

    @Inject
    public UserPersistenceUpdate(MongoClient client, @Named("dbName") String dbName) {
        super(client, dbName);
    }

    @Override
    protected Class<User> getItemsClass() {
        return User.class;
    }

    @Override
    protected String getCollectionName() {
        return "user";
    }
}
