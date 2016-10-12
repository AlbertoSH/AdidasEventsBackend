package com.github.albertosh.adidas.backend.persistence.authinfo;

import com.github.albertosh.adidas.backend.models.user.AuthInfo;
import com.github.albertosh.adidas.backend.persistence.core.PersistenceRead;
import com.mongodb.rx.client.MongoClient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class AuthInfoPersistenceRead
        extends PersistenceRead<AuthInfo>
        implements IAuthInfoPersistenceRead {

    @Inject
    public AuthInfoPersistenceRead(MongoClient client, @Named("dbName") String dbName) {
        super(client, dbName);
    }

    @Override
    protected Class<AuthInfo> getItemsClass() {
        return AuthInfo.class;
    }

    @Override
    protected String getCollectionName() {
        return "authinfo";
    }
}
