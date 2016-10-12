package com.github.albertosh.adidas.backend.models.event;

import com.google.common.base.Preconditions;

import com.github.albertosh.swagplash.annotations.ApiModel;
import com.github.albertosh.swagplash.annotations.ApiModelProperty;

import java.time.LocalDate;
import java.util.Optional;

import javax.annotation.Nullable;

@ApiModel
public class Event {

    @ApiModelProperty
    private final String id;
    @ApiModelProperty
    private final String title;
    @ApiModelProperty
    private final LocalDate date;
    @ApiModelProperty
    @Nullable
    private final String description;
    @ApiModelProperty
    @Nullable
    private final String imageUrl;
    @ApiModelProperty
    @Nullable
    private final String imageId;

    private Event(Builder builder) {
        this.id = Preconditions.checkNotNull(builder.id);
        this.title = Preconditions.checkNotNull(builder.title);
        this.date = Preconditions.checkNotNull(builder.date);
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.imageId = builder.imageId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getImageUrl() {
        return Optional.ofNullable(imageUrl);
    }

    @Nullable
    public String getImageId() {
        return imageId;
    }

    public static class Builder {
        private String id;
        private String title;
        private LocalDate date;
        private String description;
        private String imageUrl;
        private String imageId;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
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

        public Builder fromPrototype(Event prototype) {
            id = prototype.id;
            title = prototype.title;
            date = prototype.date;
            description = prototype.description;
            imageUrl = prototype.imageUrl;
            imageId = prototype.imageId;
            return this;
        }

        public Event build() {
            return new Event(this);
        }
    }
}
