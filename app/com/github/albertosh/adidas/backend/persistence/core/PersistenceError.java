package com.github.albertosh.adidas.backend.persistence.core;

public class PersistenceError extends Throwable {

    public final static PersistenceError itemNotFound = new PersistenceError("Item not found");

    private PersistenceError(String message) {
        super(message);
    }
}
