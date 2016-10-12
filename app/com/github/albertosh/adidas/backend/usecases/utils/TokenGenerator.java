package com.github.albertosh.adidas.backend.usecases.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TokenGenerator {

    private final SecureRandom random;
    private final PasswordStorage passwordStorage;

    @Inject
    public TokenGenerator(PasswordStorage passwordStorage) {
        this.passwordStorage = passwordStorage;
        this.random = new SecureRandom();
    }

    public String generate() {
        try {
            return passwordStorage.createHash(new BigInteger(130, random).toString(32));
        } catch (PasswordStorage.CannotPerformOperationException e) {
            //Shouldn't happen...
            throw new RuntimeException(e);
        }
    }

}
