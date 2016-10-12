package com.github.albertosh.adidas.backend.models;

import com.google.common.base.Preconditions;

import java.util.Optional;

public class EventTexts {

    private final String title;
    private final String description;

    private EventTexts(Builder builder) {
        this.title = Preconditions.checkNotNull(builder.title);
        this.description = builder.description;
    }

    public String getTitle() {
        return title;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }


    public static class Builder {
        private String title;
        private String description;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder fromPrototype(EventTexts prototype) {
            title = prototype.title;
            description = prototype.description;
            return this;
        }

        public EventTexts build() {
            return new EventTexts(this);
        }
    }
}
