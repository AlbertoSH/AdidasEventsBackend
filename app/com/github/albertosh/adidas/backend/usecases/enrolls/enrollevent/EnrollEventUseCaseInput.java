package com.github.albertosh.adidas.backend.usecases.enrolls.enrollevent;

import com.google.common.base.Preconditions;

public class EnrollEventUseCaseInput {

    private final String uuid;
    private final String eventId;

    private EnrollEventUseCaseInput(Builder builder) {
        this.uuid = Preconditions.checkNotNull(builder.uuid);
        this.eventId = Preconditions.checkNotNull(builder.eventId);
    }

    public String getUuid() {
        return uuid;
    }

    public String getEventId() {
        return eventId;
    }


    public static class Builder {
        private String uuid;
        private String eventId;

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public Builder fromPrototype(EnrollEventUseCaseInput prototype) {
            uuid = prototype.uuid;
            eventId = prototype.eventId;
            return this;
        }

        public EnrollEventUseCaseInput build() {
            return new EnrollEventUseCaseInput(this);
        }
    }
}
