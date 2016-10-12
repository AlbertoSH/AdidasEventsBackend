package com.github.albertosh.adidas.backend.models.user;

import com.google.common.base.Preconditions;

import com.github.albertosh.adidas.backend.persistence.core.ObjectWithId;

public class AuthInfo extends ObjectWithId {

    // TODO this tokens never expires. They should have an expiration date and a refresh token process

    private final String uuid;
    private final String token;

    private AuthInfo(Builder builder) {
        super(builder);
        this.uuid = Preconditions.checkNotNull(builder.uuid);
        this.token = Preconditions.checkNotNull(builder.token);
    }

    public String getUuid() {
        return uuid;
    }

    public String getToken() {
        return token;
    }


    public static class Builder extends ObjectWithId.Builder<AuthInfo> {
        private String uuid;
        private String token;

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder fromPrototype(AuthInfo prototype) {
            super.fromPrototype(prototype);
            uuid = prototype.uuid;
            token = prototype.token;
            return this;
        }

        public AuthInfo build() {
            return new AuthInfo(this);
        }
    }
}
