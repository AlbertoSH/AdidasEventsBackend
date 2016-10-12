package com.github.albertosh.adidas.backend.usecases.event.getevents;

import java.util.Optional;

public class GetEventsUseCaseInput {

    private final Integer page;
    private final Integer pageSize;
    private final String language;

    private GetEventsUseCaseInput(Builder builder) {
        this.page = builder.page;
        this.pageSize = builder.pageSize;
        this.language = builder.language;
    }

    public Optional<Integer> getPage() {
        return Optional.ofNullable(page);
    }

    public Optional<Integer> getPageSize() {
        return Optional.ofNullable(pageSize);
    }

    public Optional<String> getLanguage() {
        return Optional.ofNullable(language);
    }


    public static class Builder {
        private Integer page;
        private Integer pageSize;
        private String language;

        public Builder page(Integer page) {
            this.page = page;
            return this;
        }

        public Builder pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder fromPrototype(GetEventsUseCaseInput prototype) {
            page = prototype.page;
            pageSize = prototype.pageSize;
            language = prototype.language;
            return this;
        }

        public GetEventsUseCaseInput build() {
            return new GetEventsUseCaseInput(this);
        }
    }
}
