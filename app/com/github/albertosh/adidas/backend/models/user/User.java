package com.github.albertosh.adidas.backend.models.user;

import com.google.common.base.Preconditions;

import com.github.albertosh.adidas.backend.persistence.core.ObjectWithId;

import java.time.LocalDate;
import java.util.Optional;

public class User extends ObjectWithId {

    private final String email;
    private final String firstName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String country;
    private final String preferredLanguage;

    private User(Builder builder) {
        super(builder);
        this.email = Preconditions.checkNotNull(builder.email);
        this.firstName = Preconditions.checkNotNull(builder.firstName);
        this.lastName = Preconditions.checkNotNull(builder.lastName);
        this.dateOfBirth = Preconditions.checkNotNull(builder.dateOfBirth);
        this.country = Preconditions.checkNotNull(builder.country);
        this.preferredLanguage = Preconditions.checkNotNull(builder.preferredLanguage);
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }


    public static class Builder extends ObjectWithId.Builder<User> {
        private String email;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private String country;
        private String preferredLanguage;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder preferredLanguage(String preferredLanguage) {
            this.preferredLanguage = preferredLanguage;
            return this;
        }

        public Builder fromPrototype(User prototype) {
            super.fromPrototype(prototype);
            email = prototype.email;
            firstName = prototype.firstName;
            lastName = prototype.lastName;
            dateOfBirth = prototype.dateOfBirth;
            country = prototype.country;
            preferredLanguage = prototype.preferredLanguage;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
