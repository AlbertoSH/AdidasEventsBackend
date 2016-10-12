package com.github.albertosh.adidas.backend.usecases.utils.storeimage;

import com.google.common.base.Preconditions;

import java.io.File;
import java.util.Optional;

public class StoreImageUseCaseInput {

    private final String imageId;
    private final File image;

    private StoreImageUseCaseInput(Builder builder) {
        this.imageId = builder.imageId;
        this.image = Preconditions.checkNotNull(builder.image);
    }

    public Optional<String> getImageId() {
        return Optional.ofNullable(imageId);
    }

    public File getImage() {
        return image;
    }


    public static class Builder {
        private String imageId;
        private File image;

        public Builder imageId(String imageId) {
            this.imageId = imageId;
            return this;
        }

        public Builder image(File image) {
            this.image = image;
            return this;
        }

        public Builder fromPrototype(StoreImageUseCaseInput prototype) {
            imageId = prototype.imageId;
            image = prototype.image;
            return this;
        }

        public StoreImageUseCaseInput build() {
            return new StoreImageUseCaseInput(this);
        }
    }
}
