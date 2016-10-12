package com.github.albertosh.adidas.backend.persistence.utils;

import com.google.inject.ImplementedBy;

@ImplementedBy(MongoIdGenerator.class)
public interface IdGenerator {

    public String getNewId();

}
