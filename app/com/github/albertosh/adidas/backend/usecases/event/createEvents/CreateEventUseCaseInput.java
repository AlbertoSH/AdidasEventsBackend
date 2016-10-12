package com.github.albertosh.adidas.backend.usecases.event.createEvents;

import com.google.common.base.Preconditions;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;

public class CreateEventUseCaseInput {

    private final String defaultLanguage;
    private final String title;
    private final String description;
    private final LocalDate date;
    private final File image;
    private final String imageUrl;

    private CreateEventUseCaseInput(Builder builder) {
        this.defaultLanguage = builder.defaultLanguage;
        this.title = Preconditions.checkNotNull(builder.title);
        this.description = builder.description;
        this.date = Preconditions.checkNotNull(builder.date);
        this.image = builder.image;
        this.imageUrl = builder.imageUrl;
    }

    public Optional<String> getDefaultLanguage() {
        return Optional.ofNullable(defaultLanguage);
    }

    public String getTitle() {
        return title;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<File> getImage() {
        return Optional.ofNullable(image);
    }

    public Optional<String> getImageUrl() {
        return Optional.ofNullable(imageUrl);
    }


    public static class Builder {
        private String defaultLanguage;
        private String title;
        private String description;
        private LocalDate date;
        private File image;
        private String imageUrl;

        public Builder defaultLanguage(String defaultLanguage) {
            this.defaultLanguage = defaultLanguage;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder image(File image) {
            this.image = image;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder fromPrototype(CreateEventUseCaseInput prototype) {
            defaultLanguage = prototype.defaultLanguage;
            title = prototype.title;
            description = prototype.description;
            date = prototype.date;
            image = prototype.image;
            imageUrl = prototype.imageUrl;
            return this;
        }

        public CreateEventUseCaseInput build() {
            return new CreateEventUseCaseInput(this);
        }
    }
}
