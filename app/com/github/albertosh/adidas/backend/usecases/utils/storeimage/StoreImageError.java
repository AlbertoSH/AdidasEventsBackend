package com.github.albertosh.adidas.backend.usecases.utils.storeimage;

import com.github.albertosh.adidas.backend.persistence.core.PersistenceError;

public class StoreImageError extends Throwable {

    public final static StoreImageError notAnImage = new StoreImageError("Not an image");


    private StoreImageError(String message) {
        super(message);
    }

}
