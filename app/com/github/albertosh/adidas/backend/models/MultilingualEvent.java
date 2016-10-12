package com.github.albertosh.adidas.backend.models;

import com.google.common.base.Preconditions;

import com.github.albertosh.adidas.backend.persistence.core.ObjectWithId;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MultilingualEvent extends ObjectWithId {

    private final LocalDate date;
    private final String imageUrl;
    private final String imageId;
    private final Map<String, EventTexts> texts;
    private final String defaultLanguage;

    private MultilingualEvent(Builder builder) {
        super(builder);
        this.date = Preconditions.checkNotNull(builder.date);
        this.imageUrl = builder.imageUrl;
        this.imageId = builder.imageId;
        this.texts = Preconditions.checkNotNull(builder.texts);
        this.defaultLanguage = Preconditions.checkNotNull(builder.defaultLanguage);
        if (texts.keySet().isEmpty())
            throw new IllegalStateException("A MultilingualEvent must have at least one EventTexts");
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<String> getImageUrl() {
        return Optional.ofNullable(imageUrl);
    }

    public Optional<String> getImageId() {
        return Optional.ofNullable(imageId);
    }

    public Map<String, EventTexts> getTexts() {
        return texts;
    }

    public Event getDefaultLanguageEvent() {
        return getLocalizedEvent(defaultLanguage).get();
    }

    public Optional<Event> getLocalizedEvent(String language) {
        if (texts.containsKey(language)) {
            EventTexts localizedTexts = texts.get(language);
            return Optional.of(new Event.Builder()
                    .id(getId())
                    .date(date)
                    .imageUrl(imageUrl)
                    .imageId(imageId)
                    .title(localizedTexts.getTitle())
                    .description(localizedTexts.getDescription().orElse(null))
                    .build());
        } else {
            return Optional.empty();
        }
    }

    public Event getLocalizedOrDefaultEvent(String language) {
        return getLocalizedEvent(language)
                .orElseGet(this::getDefaultLanguageEvent);
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public static class Builder extends ObjectWithId.Builder<MultilingualEvent> {
        private LocalDate date;
        private String imageUrl;
        private String imageId;
        private Map<String, EventTexts> texts = new HashMap<>();
        private String defaultLanguage;

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder imageId(String imageId) {
            this.imageId = imageId;
            return this;
        }

        public Builder withText(String language, EventTexts texts) {
            this.texts.put(language, texts);
            return this;
        }


        /**
         * Use with careful. If the multilingual event created doesn't have texts for this language
         * it will throw an exception
         *
         * @deprecated use {@link #defaultLanguage(String, EventTexts)} for safety reasons. This
         * method is available to be used at codec level
         */
        @Deprecated
        public Builder defaultLanguage(String defaultLanguage) {
            this.defaultLanguage = defaultLanguage;
            return this;
        }

        public Builder defaultLanguage(String defaultLanguage, EventTexts texts) {
            this.defaultLanguage = defaultLanguage;
            this.texts.put(defaultLanguage, texts);
            return this;
        }

        public Builder fromPrototype(MultilingualEvent prototype) {
            super.fromPrototype(prototype);
            date = prototype.date;
            imageUrl = prototype.imageUrl;
            imageId = prototype.imageId;
            texts = prototype.texts;
            defaultLanguage = prototype.defaultLanguage;
            return this;
        }

        public MultilingualEvent build() {
            return new MultilingualEvent(this);
        }
    }
}
