package com.github.albertosh.adidas.backend.models;

import com.google.common.base.Preconditions;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MultilingualEvent {

    private final String id;
    private final LocalDate date;
    private final String imageUrl;
    private final Map<String, EventTexts> texts;
    private final String defaultLanguage;

    private MultilingualEvent(Builder builder) {
        this.id = Preconditions.checkNotNull(builder.id);
        this.date = Preconditions.checkNotNull(builder.date);
        this.imageUrl = builder.imageUrl;
        this.texts = Preconditions.checkNotNull(builder.texts);
        this.defaultLanguage = Preconditions.checkNotNull(builder.defaultLanguage);
        if (texts.keySet().isEmpty())
            throw new IllegalStateException("A MultilingualEvent must have at least one EventTexts");
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<String> getImageUrl() {
        return Optional.ofNullable(imageUrl);
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
                    .id(id)
                    .date(date)
                    .imageUrl(imageUrl)
                    .title(localizedTexts.getTitle())
                    .description(localizedTexts.getDescription().orElse(null))
                    .build());
        } else {
            return Optional.empty();
        }
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public static class Builder {
        private String id;
        private LocalDate date;
        private String imageUrl;
        private Map<String, EventTexts> texts = new HashMap<>();
        private String defaultLanguage;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
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
            id = prototype.id;
            date = prototype.date;
            imageUrl = prototype.imageUrl;
            texts = prototype.texts;
            defaultLanguage = prototype.defaultLanguage;
            return this;
        }

        public MultilingualEvent build() {
            return new MultilingualEvent(this);
        }
    }
}
