package com.github.albertosh.adidas.backend.persistence.utils;

import org.bson.types.ObjectId;

public class MongoIdGenerator implements IdGenerator {

    @Override
    public String getNewId() {
        return new ObjectId().toString();
    }

}
